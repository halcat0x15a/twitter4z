package twitter4z

import scalaz._
import Scalaz._
import scalaj.http._
import net.liftweb.json.scalaz.JsonScalaz._
import http._
import objects._
import json._

package object api {

  def param[A: Show](key: String) = ((value: Param[A]) => key -> value.map(_.shows))

  val TrimUser = param[Boolean]("trim_user")

  val IncludeEntities = param[Boolean]("include_entities")

  val Count = param[Int]("count")

  val SinceId = param[ID]("since_id")

  val MaxId = param[ID]("max_id")

  val Page = param[Int]("page")

  val IncludeRts = param[Boolean]("include_rts")

  val ContributorDetails = param[Boolean]("contributor_details")

  val ExcludeReplies = param[Boolean]("exclude_replies")

  val StringifyIds = param[Boolean]("stringify_ids")

  val Status = param[String]("status")

  val InReplyToStatusId = param[ID]("in_reply_to_status_id")

  val PlaceId = param[String]("place_id")

  val DisplayCoordinates = param[Boolean]("display_coordinates")

  val WrapLinks = param[Boolean]("wrap_links")

  type UserID = Either[String, ID]

  def UserID(userId: UserID) = userId.fold("screen_name" -> New(_), "user_id" -> _.shows.pure[Param])

  type Statuses = List[Status]

  case class Location(lat: Double, long: Double) {
    def list = List("lat" -> New(lat.shows), "long" -> New(long.shows))
  }

  def resource[A](method: Method, string: String, tokens: Option[Tokens], optionalParameters: (String, Param[String])*)(implicit timeout: Timeout, jsonr: JSONR[A]) = {
    val params = optionalParameters.collect {
      case (k, New(v)) => k -> v
    }
    val request = method("http://api.twitter.com/1/" + string + ".json").params(params: _*)
    tokens.fold(request.oauth(_), request)(parse[A])
  }

}
