package twitter4z

import java.io._
import org.joda.time.format._
import scalaz._
import Scalaz._
import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._
import objects._

package object json {

  implicit def CoordinatesJSONR = JSONR[Coordinates]((Coordinates.apply _).applyJSON(field("coordinates"), field("type")))

  implicit def UrlJSONR = JSONR[Url]((Url.apply _).applyJSON(field("url"), field("display_url"), field("expanded_url"), field("indices")))

  implicit def UserMentionJSONR = JSONR[UserMention]((UserMention.apply _).applyJSON(field("id"), field("screen_name"), field("name"), field("indices")))

  implicit def HashtagJSONR = JSONR[Hashtag]((Hashtag.apply _).applyJSON(field("text"), field("indices")))

  implicit def EntitiesJSONR = JSONR[Entities]((Entities.apply _).applyJSON(field("urls"), field("user_mentions"), field("hashtags")))

  implicit def PlaceTypeJSONR = jsonr[PlaceType] {
    case "poi" => Poi
    case "neighborhood" => Neighborhood
    case "city" => City
    case "admin" => Admin
    case "country" => Country
  }

  implicit def PlaceJSONR = JSONR[Place]((Place.apply _).applyJSON(field("country"), field("country_code"), field("full_name"), field("id"), field("name"), field("place_type"), field("url")))

  implicit def CountJSON = JSONR[Count] {
    case JInt(int) => int.toInt.right[String].success
    case JString(string) => string.left[Int].success
    case x => UnexpectedJSONError(x, classOf[JInt]).fail.liftFailNel
  }

  implicit object UserJSONR extends JSONR[User] {
    def read(json: JValue) = for {
      x <- field[Boolean]("contributors_enabled")(json).map((User.apply _).curried)
      x <- field[DateTime]("created_at")(json).map(x)
      x <- field[Option[String]]("description")(json).map(x)
      x <- field[Int]("favourites_count")(json).map(x)
      x <- field[Option[Boolean]]("follow_request_sent")(json).map(x)
      x <- field[Int]("followers_count")(json).map(x)
      x <- field[Int]("friends_count")(json).map(x)
      x <- field[Boolean]("geo_enabled")(json).map(x)
      x <- field[ID]("id")(json).map(x)
      x <- field[Boolean]("is_translator")(json).map(x)
      x <- field[String]("lang")(json).map(x)
      x <- field[Int]("listed_count")(json).map(x)
      x <- field[Option[String]]("location")(json).map(x)
      x <- field[String]("name")(json).map(x)
      x <- {
	val profile = for {
	  x <- field[String]("profile_background_color")(json).map((Profile.apply _).curried)
	  x <- field[URL]("profile_background_image_url")(json).map(x)
	  x <- field[Boolean]("profile_background_tile")(json).map(x)
	  x <- field[URL]("profile_image_url")(json).map(x)
	  x <- field[String]("profile_link_color")(json).map(x)
	  x <- field[String]("profile_sidebar_border_color")(json).map(x)
	  x <- field[String]("profile_sidebar_fill_color")(json).map(x)
	  x <- field[String]("profile_text_color")(json).map(x)
	  x <- field[Boolean]("profile_use_background_image")(json).map(x)
	} yield x
	profile.map(x)
      }
      x <- field[String]("screen_name")(json).map(x)
      x <- field[Boolean]("show_all_inline_media")(json).map(x)
    } yield x
  }

  implicit object StatusJSONR extends JSONR[Status] {
    def read(json: JValue) = for {
      x <- field[Option[List[ID]]]("contributors")(json).map((Status.apply _).curried)
      x <- field[Option[Coordinates]]("coordinates")(json).map(x)
      x <- field[DateTime]("created_at")(json).map(x)
      x <- fieldOpt[Entities]("entities")(json).map(x)
      x <- field[Boolean]("favorited")(json).map(x)
      x <- field[ID]("id")(json).map(x)
      x <- field[Option[String]]("in_reply_to_screen_name")(json).map(x)
      x <- field[Option[ID]]("in_reply_to_status_id")(json).map(x)
      x <- field[Option[ID]]("in_reply_to_user_id")(json).map(x)
      x <- field[Option[Place]]("place")(json).map(x)
      x <- field[Count]("retweet_count")(json).map(x)
      x <- field[Boolean]("retweeted")(json).map(x)
      x <- fieldOpt[Status]("retweeted_status")(json).map(x)
      x <- field[String]("source")(json).map(x)
      x <- field[String]("text")(json).map(x)
      x <- field[Boolean]("truncated")(json).map(x)
      x <- field[User]("user")(json)(UserJSONR).map(x)
    } yield x
  }

  implicit def ResizeJSONR = jsonr[Resize] {
    case "fit" => Fit
    case "crop" => Crop
  }

  implicit def SizeJSONR = JSONR[Size]((Size.apply _).applyJSON(field("w"), field("h"), field("resize")))

  implicit def SizesJSONR = JSONR[Sizes]((Sizes.apply _).applyJSON(field("large"), field("medium"), field("small"), field("thumb")))

  implicit def MediaType = jsonr[MediaType] {
    case "photo" => Photo
  }

  implicit def MediaJSONR = JSONR((Media.apply _).applyJSON(field("id"), field("media_url"), field("url"), field("display_url"), field("expanded_url"), field("sizes"), field("type"), field("indices")))

  implicit def URLJSONR: JSONR[URL] = jsonr(new URL(_))

  implicit def DateTimeJSONR: JSONR[DateTime] = jsonr(DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss Z yyyy").withLocale(java.util.Locale.ENGLISH).parseDateTime)

  def fieldOpt[A: JSONR](name: String)(json: JValue): Result[Option[A]] = field[A](name)(json) match {
    case f@Failure(nel) => nel.head match {
      case NoSuchFieldError(_, _) => Success(None)
      case _ => f.lift[Option, A]
    }
    case s: Success[_, _] => s.map(Some.apply)
  }

  def JSONR[A](f: JValue => Result[A]): JSONR[A] = new JSONR[A] {
    def read(json: JValue) = f(json)
  }

  def jsonr[A](f: String => A): JSONR[A] = new JSONR[A] {
    def read(json: JValue) = json match {
      case JString(string) => success(f(string))
      case x => UnexpectedJSONError(x, classOf[JString]).fail.liftFailNel
    }
  }

  def parse(input: InputStream) = JsonParser.parse(new InputStreamReader(input))

}
