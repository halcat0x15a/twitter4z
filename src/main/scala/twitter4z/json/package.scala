package twitter4z

import java.io._
import scalaz._
import Scalaz._
import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._

package object json {

  import objects._

  implicit def Func10ToJSON[A: JSONR, B: JSONR, C: JSONR, D: JSONR, E: JSONR, F: JSONR, G: JSONR, H: JSONR, I: JSONR, J: JSONR, R](z: (A, B, C, D, E, F, G, H, I, J) => R) = new {
    def applyJSON(a: JValue => Result[A], b: JValue => Result[B], c: JValue => Result[C], d: JValue => Result[D], e: JValue => Result[E], f: JValue => Result[F], g: JValue => Result[G], h: JValue => Result[H], i: JValue => Result[I], j: JValue => Result[J]): JValue => Result[R] =
      (json: JValue) => (a(json) |@| b(json) |@| c(json) |@| d(json) |@| e(json) |@| f(json) |@| g(json) |@| h(json) |@| i(json) |@| j(json))(z)
  }

  implicit def Func11ToJSON[A: JSONR, B: JSONR, C: JSONR, D: JSONR, E: JSONR, F: JSONR, G: JSONR, H: JSONR, I: JSONR, J: JSONR, K: JSONR, R](z: (A, B, C, D, E, F, G, H, I, J, K) => R) = new {
    def applyJSON(a: JValue => Result[A], b: JValue => Result[B], c: JValue => Result[C], d: JValue => Result[D], e: JValue => Result[E], f: JValue => Result[F], g: JValue => Result[G], h: JValue => Result[H], i: JValue => Result[I], j: JValue => Result[J], k: JValue => Result[K]): JValue => Result[R] =
      (json: JValue) => (a(json) |@| b(json) |@| c(json) |@| d(json) |@| e(json) |@| f(json) |@| g(json) |@| h(json) |@| i(json) |@| j(json) |@| k(json))(z)
  }

  implicit def Func12ToJSON[A: JSONR, B: JSONR, C: JSONR, D: JSONR, E: JSONR, F: JSONR, G: JSONR, H: JSONR, I: JSONR, J: JSONR, K: JSONR, L: JSONR, R](z: (A, B, C, D, E, F, G, H, I, J, K, L) => R) = new {
    def applyJSON(a: JValue => Result[A], b: JValue => Result[B], c: JValue => Result[C], d: JValue => Result[D], e: JValue => Result[E], f: JValue => Result[F], g: JValue => Result[G], h: JValue => Result[H], i: JValue => Result[I], j: JValue => Result[J], k: JValue => Result[K], l: JValue => Result[L]): JValue => Result[R] =
      (json: JValue) => (a(json) |@| b(json) |@| c(json) |@| d(json) |@| e(json) |@| f(json) |@| g(json) |@| h(json) |@| i(json) |@| j(json) |@| k(json) |@| l(json))(z)
  }

  implicit object CoordinatesJSONR extends JSONR[Coordinates] {
    def read(json: JValue) = (Coordinates.apply _).applyJSON(field("coordinates"), field("type"))(json)
  }

  implicit object UrlJSONR extends JSONR[Url] {
    def read(json: JValue) = (Url.apply _).applyJSON(field("url"), field("display_url"), field("expanded_url"), field("indices"))(json)
  }

  implicit object UserMentionJSONR extends JSONR[UserMention] {
    def read(json: JValue) = (UserMention.apply _).applyJSON(field("id"), field("screen_name"), field("name"), field("indices"))(json)
  }

  implicit object HashtagJSONR extends JSONR[Hashtag] {
    def read(json: JValue) = (Hashtag.apply _).applyJSON(field("text"), field("indices"))(json)
  }

  implicit object EntitiesJSONR extends JSONR[Entities] {
    def read(json: JValue) = (Entities.apply _).applyJSON(field("urls"), field("user_mentions"), field("hashtags"))(json)
  }

  implicit def PlaceTypeJSONR = jsonr[PlaceType] {
    case "poi" => Poi
    case "neighborhood" => Neighborhood
    case "city" => City
    case "admin" => Admin
    case "country" => Country
  }

  implicit object PlaceJSONR extends JSONR[Place] {
    def read(json: JValue) = (Place.apply _).applyJSON(field("country"), field("country_code"), field("full_name"), field("id"), field("name"), field("place_type"), field("url"))(json)
  }

  implicit object CountJSON extends JSONR[Count] {
    def read(json: JValue) = json match {
      case JInt(int) => int.toInt.right[String].success
      case JString(string) => string.left[Int].success
      case x => UnexpectedJSONError(x, classOf[JInt]).fail.liftFailNel
    }
  }

  implicit object UserJSONR extends JSONR[User] {
    def read(json: JValue) = for {
      x <- field[Boolean]("contributors_enabled")(json).map((User.apply _).curried)
      x <- field[String]("created_at")(json).map(x)
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
      x <- field[String]("created_at")(json).map(x)
      x <- field[Entities]("entities")(json).map(x)
      x <- field[Boolean]("favorited")(json).map(x)
      x <- field[ID]("id")(json).map(x)
      x <- field[Option[String]]("in_reply_to_screen_name")(json).map(x)
      x <- field[Option[ID]]("in_reply_to_status_id")(json).map(x)
      x <- field[Option[ID]]("in_reply_to_user_id")(json).map(x)
      x <- field[Option[Place]]("place")(json).map(x)
      x <- field[Count]("retweet_count")(json).map(x)
      x <- field[Boolean]("retweeted")(json).map(x)
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

  implicit object SizeJSONR extends JSONR[Size] {
    def read(json: JValue) = (Size.apply _).applyJSON(field("w"), field("h"), field("resize"))(json)
  }

  implicit object SizesJSONR extends JSONR[Sizes] {
    def read(json: JValue) = (Sizes.apply _).applyJSON(field("large"), field("medium"), field("small"), field("thumb"))(json)
  }

  implicit def MediaType = jsonr[MediaType] {
    case "photo" => Photo
  }

  implicit object MediaJSONR extends JSONR[Media] {
    def read(json: JValue) = (Media.apply _).applyJSON(field("id"), field("media_url"), field("url"), field("display_url"), field("expanded_url"), field("sizes"), field("type"), field("indices"))(json)
  }

  implicit def URLJSONR: JSONR[URL] = jsonr(new URL(_))

  def jsonr[A](f: String => A): JSONR[A] = new JSONR[A] {
    def read(json: JValue) = json match {
      case JString(string) => success(f(string))
      case x => UnexpectedJSONError(x, classOf[JString]).fail.liftFailNel
    }
  }

  def parse[A: JSONR]: InputStream => Result[A] = { input =>
    fromJSON[A](JsonParser.parse(new InputStreamReader(input)))
  }

}
