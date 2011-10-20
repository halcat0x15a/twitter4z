package twitter4z.api

import scalaz._
import Scalaz._

sealed trait Parameter extends NewType[(String, String)]

sealed abstract class AbstractParameter[+A: Show](key: String) extends Parameter with Product1[A] {

  val value = key -> _1.shows

}

trait Parameters {

  private type SLong = scala.Long

  case class TrimUser(_1: Boolean) extends AbstractParameter[Boolean]("trim_user")
  implicit def WrapTrimUser(trimUser: Boolean) = TrimUser(trimUser)
  case class IncludeEntities(_1: Boolean) extends AbstractParameter[Boolean]("include_entities")
  implicit def WrapIncludeEntities(includeEntities: Boolean) = IncludeEntities(includeEntities)
  case class Count(_1: Int) extends AbstractParameter[Int]("count")
  implicit def WrapCount(count: Int) = Count(count)
  case class SinceId(_1: SLong) extends AbstractParameter[SLong]("since_id")
  implicit def WrapSinceId(sinceId: SLong) = SinceId(sinceId)
  case class MaxId(_1: SLong) extends AbstractParameter[SLong]("max_id")
  implicit def WrapMaxId(maxId: SLong) = MaxId(maxId)
  case class Page(_1: Int) extends AbstractParameter[Int]("page")
  implicit def WrapPage(page: Int) = Page(page)
  case class IncludeRts(_1: Boolean) extends AbstractParameter[Boolean]("include_rts")
  implicit def WrapIncludeRts(includeRts: Boolean) = IncludeRts(includeRts)
  case class ContributorDetails(_1: Boolean) extends AbstractParameter[Boolean]("contributor_details")
  implicit def WrapContributorDetails(contributorDetails: Boolean) = ContributorDetails(contributorDetails)
  case class ExcludeReplies(_1: Boolean) extends AbstractParameter[Boolean]("exclude_replies")
  implicit def WrapExcludeReplies(excludeReplies: Boolean) = ExcludeReplies(excludeReplies)
  case class StringifyIds(_1: Boolean) extends AbstractParameter[Boolean]("stringify_ids")
  implicit def WrapStringifyIds(stringifyIds: Boolean) = StringifyIds(stringifyIds)
  case class Status(_1: String) extends AbstractParameter[String]("status")
  implicit def WrapStatus(status: String) = Status(status)
  case class InReplyToStatusId(_1: SLong) extends AbstractParameter[SLong]("in_reply_to_status_id")
  implicit def WrapInReplyToStatusId(inReplyToStatusId: SLong) = InReplyToStatusId(inReplyToStatusId)
  case class PlaceId(_1: String) extends AbstractParameter[String]("place_id")
  implicit def WrapPlaceId(placeId: String) = PlaceId(placeId)
  case class DisplayCoordinates(_1: Boolean) extends AbstractParameter[Boolean]("display_coordinates")
  implicit def WrapDisplayCoordinates(displayCoordinates: Boolean) = DisplayCoordinates(displayCoordinates)
  case class WrapLinks(_1: Boolean) extends AbstractParameter[Boolean]("wrap_links")
  implicit def WrapWrapLinks(wrapLinks: Boolean) = WrapLinks(wrapLinks)
  case class UserId(_1: SLong) extends AbstractParameter[SLong]("user_id")
  implicit def WrapUserId(userId: SLong) = UserId(userId)
  case class ScreenName(_1: String) extends AbstractParameter[String]("screen_name")
  implicit def WrapScreenName(screenName: String) = ScreenName(screenName)
  case class Lat(_1: Double) extends AbstractParameter[Double]("lat")
  implicit def WrapLat(lat: Double) = Lat(lat)
  case class Long(_1: Double) extends AbstractParameter[Double]("long")
  implicit def WrapLong(long: Double) = Long(long)

  implicit def EitherToParameter(either: Either[Parameter, Parameter]): Parameter = either.fold(identity, identity)

}
