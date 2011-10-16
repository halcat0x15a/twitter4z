package twitter4z

import scalaz._
import Scalaz._
import scalaj.http._
import net.liftweb.json.scalaz.JsonScalaz._
import http._
import objects._
import json._

package object api {

  type JInt = java.lang.Integer
  type JLong = java.lang.Long
  type JBoolean = java.lang.Boolean

  implicit def ShowJInt = showA[JInt]
  implicit def ShowJLong = showA[JLong]
  implicit def ShowJBoolean = showA[JBoolean]

  val HomeTimeline = "statuses/home_timeline"
  val Mentions = "statuses/mentions"
  val PublicTimeline = "statuses/public_timeline"
  val RetweetedByMe = "statuses/retweeted_by_me"
  val RetweetedToMe = "statuses/retweeted_to_me"
  val RetweetsOfMe = "statuses/retweets_of_me"
  val UserTimeline = "statuses/user_timeline"
  val RetweetedToUser = "statuses/retweeted_to_user"
  val RetweetedByUser = "statuses/retweeted_by_user"

  val TrimUser = param[JBoolean]("trim_user")
  val IncludeEntities = param[JBoolean]("include_entities")
  val Count = param[JInt]("count")
  val SinceId = param[JLong]("since_id")
  val MaxId = param[JLong]("max_id")
  val Page = param[JInt]("page")
  val IncludeRts = param[JBoolean]("include_rts")
  val ContributorDetails = param[JBoolean]("contributor_details")
  val ExcludeReplies = param[JBoolean]("exclude_replies")
  val StringifyIds = param[JBoolean]("stringify_ids")
  val Status = param[String]("status")
  val InReplyToStatusId = param[JLong]("in_reply_to_status_id")
  val PlaceId = param[String]("place_id")
  val DisplayCoordinates = param[JBoolean]("display_coordinates")
  val WrapLinks = param[JBoolean]("wrap_links")

  def param[A: Show](key: String) = ((value: A) => Option(value).map(value => key -> value.shows))

  type UserID = Either[String, ID]

  def UserID(userId: UserID) = Option(userId).map(_.fold("screen_name" -> _, "user_id" -> _.shows))

  type Statuses = List[Status]

  case class Location(lat: Double, long: Double)

  object Location {
    def apply(location: Location) = Option(location).map {
      case Location(lat, long) => List("lat" -> lat.shows, "long" -> long.shows).map(Option.apply)
    } | Nil
  }

  def resource[A](method: Method, string: String, tokens: Option[Tokens], optionalParameters: Option[(String, String)]*)(implicit jsonr: JSONR[A]) = {
    val params = optionalParameters.collect {
      case Some(param) => param
    }
    val request = method("http://api.twitter.com/1/" + string + ".json").params(params: _*)
    val jvalue = tokens.fold(request.oauth(_), request)(parse)
    fromJSON[A](jvalue).map(_.set(jvalue))
  }

}
