package twitter4z

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

  implicit def PlaceTypeJSONR = jsonr[Place.Type] {
    case "poi" => Place.Poi
    case "neighborhood" => Place.Neighborhood
    case "city" => Place.City
    case "admin" => Place.Admin
    case "country" => Place.Country
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
      x <- field[Long]("id")(json).map(x)
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
      x <- field[Option[List[Long]]]("contributors")(json).map((Status.apply _).curried)
      x <- field[Option[Coordinates]]("coordinates")(json).map(x)
      x <- field[DateTime]("created_at")(json).map(x)
      x <- fieldOpt[Entities]("entities")(json).map(x)
      x <- field[Boolean]("favorited")(json).map(x)
      x <- field[Long]("id")(json).map(x)
      x <- field[Option[String]]("in_reply_to_screen_name")(json).map(x)
      x <- field[Option[Long]]("in_reply_to_status_id")(json).map(x)
      x <- field[Option[Long]]("in_reply_to_user_id")(json).map(x)
      x <- field[Option[Place]]("place")(json).map(x)
      x <- field[Count]("retweet_count")(json).map(x)
      x <- field[Boolean]("retweeted")(json).map(x)
      x <- fieldOpt[Status]("retweeted_status")(json).map(x)
      x <- field[String]("source")(json).map(x)
      x <- field[String]("text")(json).map(x)
      x <- field[Boolean]("truncated")(json).map(x)
      x <- field[User]("user")(json).map(x)
    } yield x
  }

  implicit def ResizeJSONR = jsonr[Resize] {
    case "fit" => Fit
    case "crop" => Crop
  }

  implicit def SizeJSONR =  JSONR[Size]((Size.apply _).applyJSON(field("w"), field("h"), field("resize")))

  implicit def SizesJSONR = JSONR[Sizes]((Sizes.apply _).applyJSON(field("large"), field("medium"), field("small"), field("thumb")))

  implicit def MediaType = jsonr[MediaType] {
    case "photo" => Photo
  }

  implicit def MediaJSONR = JSONR((Media.apply _).applyJSON(field("id"), field("media_url"), field("url"), field("display_url"), field("expanded_url"), field("sizes"), field("type"), field("indices")))

  implicit def URLJSONR: JSONR[URL] = jsonr(new URL(_))

  implicit def DateTimeJSONR: JSONR[DateTime] = jsonr(DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss Z yyyy").withLocale(java.util.Locale.ENGLISH).parseDateTime)

  implicit def TweetJSONR = JSONR[Tweet](json => (field[String]("created_at")(json) |@| field[String]("from_user")(json) |@| field[Long]("from_user_id")(json) |@| field[Long]("id")(json) |@| field[String]("iso_language_code")(json) |@| field[URL]("profile_image_url")(json) |@| field[String]("source")(json) |@| field[String]("text")(json) |@| fieldOpt[String]("to_user")(json) |@| field[Option[Long]]("to_user_id")(json))(Tweet.apply))

  implicit def QueryResultJSONR = JSONR((QueryResult.apply _).applyJSON(field("completed_in"), field("max_id"), field("next_page"), field("page"), field("query"), field("refresh_url"), field("results")))

  implicit def DirectMessageJSONR = JSONR(json => (field[DateTime]("created_at")(json) |@| field[Long]("id")(json) |@| field[User]("recipient")(json) |@| field[Long]("recipient_id")(json) |@| field[String]("recipient_screen_name")(json) |@| field[User]("sender")(json) |@| field[Long]("sender_id")(json) |@| field[String]("sender_screen_name")(json) |@| field[String]("text")(json)) { DirectMessage })

  implicit def SourceJSONR = JSONR(json => (field[Long]("id")(json) |@| field[String]("screen_name")(json) |@| field[Boolean]("following")(json) |@| field[Boolean]("followed_by")(json) |@| field[Boolean]("blocking")(json) |@| field[Boolean]("can_dm")(json) |@| field[Boolean]("marked_spam")(json) |@| field[Boolean]("all_replies")(json) |@| field[Boolean]("want_retweets")(json) |@| field[Boolean]("notifications_enabled")(json))(Source.apply))

  implicit def TargetJSONR = JSONR((Target.apply _).applyJSON(field("id"), field("screen_name"), field("following"), field("followed_by")))

  implicit def RelationshipJSONR = JSONR((Relationship.apply _).applyJSON(field("source"), field("target")))

  implicit def IdsJSONR = JSONR((Ids.apply _).applyJSON(field("ids"), field("next_cursor"), field("previous_cursor")))

  implicit def FriendshipJSONR = JSONR((Friendship.apply _).applyJSON(field("id"), field("screen_name"), field("name"), field("connections")))

  implicit def CategoryJSONR = JSONR((objects.Category.apply _).applyJSON(field("name"), field("slug"), field("size"), fieldOpt("users")))

  implicit def UserListJSONR = JSONR(json => (field[String]("description")(json) |@| field[String]("full_name")(json) |@| field[Long]("id")(json) |@| field[Int]("member_count")(json) |@| field[String]("name")(json) |@| field[String]("slug")(json) |@| field[Int]("subscriber_count")(json) |@| field[String]("uri")(json) |@| field[User]("user")(json) |@| field[Boolean]("following")(json) |@| field[String]("mode")(json))(UserList.apply))

  implicit def UserListsJSONR = JSONR((UserLists.apply _).applyJSON(field("lists"), field("next_cursor"), field("previous_cursor")))

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

}
