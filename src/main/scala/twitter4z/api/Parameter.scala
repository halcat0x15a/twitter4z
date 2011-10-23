package twitter4z.api

import scalaz._
import Scalaz._

sealed trait Parameter extends NewType[(String, String)]

sealed abstract class AbstractParameter[+A: Show](key: String) extends Parameter with Product1[A] {

  val value = key -> _1.shows

}

trait Parameters {

  case class Count(_1: Int) extends AbstractParameter[Int]("count")
  implicit def WrapCount(count: Int) = Count(count)
  case class ContributorDetails(_1: Boolean) extends AbstractParameter[Boolean]("contributor_details")
  implicit def WrapContributorDetails(contributorDetails: Boolean) = ContributorDetails(contributorDetails)
  case class DisplayCoordinates(_1: Boolean) extends AbstractParameter[Boolean]("display_coordinates")
  implicit def WrapDisplayCoordinates(displayCoordinates: Boolean) = DisplayCoordinates(displayCoordinates)
  case class ExcludeReplies(_1: Boolean) extends AbstractParameter[Boolean]("exclude_replies")
  implicit def WrapExcludeReplies(excludeReplies: Boolean) = ExcludeReplies(excludeReplies)
  case class Geocode(lat: Double, long: Double, radius: Double, unit: Unit) extends Parameter {
    val value = "geocode" -> (lat + "," + long + "," + radius + unit)
  }
  case class IncludeEntities(_1: Boolean) extends AbstractParameter[Boolean]("include_entities")
  implicit def WrapIncludeEntities(includeEntities: Boolean) = IncludeEntities(includeEntities)
  case class IncludeRts(_1: Boolean) extends AbstractParameter[Boolean]("include_rts")
  implicit def WrapIncludeRts(includeRts: Boolean) = IncludeRts(includeRts)
  case class InReplyToStatusId(_1: Long) extends AbstractParameter[Long]("in_reply_to_status_id")
  implicit def WrapInReplyToStatusId(inReplyToStatusId: Long) = InReplyToStatusId(inReplyToStatusId)
  case class Lang(_1: String) extends AbstractParameter[String]("lang")
  implicit def WrapLang(lang: String) = Lang(lang)
  implicit def LocaleToLang(locale: java.util.Locale) = Lang(locale.getLanguage)
  case class Latitude(_1: Double) extends AbstractParameter[Double]("lat")
  implicit def WrapLatitude(lat: Double) = Latitude(lat)
  case class Locale(_1: String) extends AbstractParameter[String]("locale")
  implicit def WrapLocale(locale: String) = Locale(locale)
  implicit def LocaleToLocale(locale: java.util.Locale) = Locale(locale.getLanguage)
  case class Longitude(_1: Double) extends AbstractParameter[Double]("long")
  implicit def WrapLongitude(long: Double) = Longitude(long)
  case class MaxId(_1: Long) extends AbstractParameter[Long]("max_id")
  implicit def WrapMaxId(maxId: Long) = MaxId(maxId)
  case class Page(_1: Int) extends AbstractParameter[Int]("page")
  implicit def WrapPage(page: Int) = Page(page)
  case class PlaceId(_1: String) extends AbstractParameter[String]("place_id")
  implicit def WrapPlaceId(placeId: String) = PlaceId(placeId)
  case class Query(_1: String) extends AbstractParameter[String]("q")
  implicit def WrapQuery(q: String) = Query(q)
  case class ResultType(_1: String) extends AbstractParameter[String]("result_type")
  implicit def WrapResultType(resultType: String) = ResultType(resultType)
  case class Rpp(_1: Int) extends AbstractParameter[Int]("rpp")
  implicit def WrapRpp(rpp: Int) = Rpp(rpp)
  case class ScreenName(_1: String) extends AbstractParameter[String]("screen_name")
  implicit def WrapScreenName(screenName: String) = ScreenName(screenName)
  case class ShowUser(_1: Boolean) extends AbstractParameter[Boolean]("show_user")
  implicit def WrapShowUser(showUser: Boolean) = ShowUser(showUser)
  case class SinceId(_1: Long) extends AbstractParameter[Long]("since_id")
  implicit def WrapSinceId(sinceId: Long) = SinceId(sinceId)
  case class Status(_1: String) extends AbstractParameter[String]("status")
  implicit def WrapStatus(status: String) = Status(status)
  case class StringifyIds(_1: Boolean) extends AbstractParameter[Boolean]("stringify_ids")
  implicit def WrapStringifyIds(stringifyIds: Boolean) = StringifyIds(stringifyIds)
  case class TrimUser(_1: Boolean) extends AbstractParameter[Boolean]("trim_user")
  implicit def WrapTrimUser(trimUser: Boolean) = TrimUser(trimUser)
  case class Until(_1: String) extends AbstractParameter[String]("until")
  implicit def WrapUntil(until: String) = Until(until)
  case class UserId(_1: Long) extends AbstractParameter[Long]("user_id")
  implicit def WrapUserId(userId: Long) = UserId(userId)
  case class WithTwitterUserId(_1: Boolean) extends AbstractParameter[Boolean]("with_twitter_user_id")
  implicit def WrapWithTwitterUserId(withTwitterUserId: Boolean) = WithTwitterUserId(withTwitterUserId)
  case class WrapLinks(_1: Boolean) extends AbstractParameter[Boolean]("wrap_links")
  implicit def WrapWrapLinks(wrapLinks: Boolean) = WrapLinks(wrapLinks)

  sealed abstract class Unit(val value: String) extends NewType[String]
  case object Kilometers extends Unit("km")
  case object Miles extends Unit("mi")

  implicit def EitherToParameter(either: Either[Parameter, Parameter]): Parameter = either.fold(identity, identity)

  type Id = Either[ScreenName, UserId]

  implicit def UserIdToId(userId: Long) = UserId(userId).right[ScreenName]

  implicit def StringToId(screenName: String) = ScreenName(screenName).left[UserId]

}
