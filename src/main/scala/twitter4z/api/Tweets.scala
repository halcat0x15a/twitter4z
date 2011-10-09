package twitter4z.api

import scalaz._
import Scalaz._
import net.liftweb.json.scalaz.JsonScalaz._
import twitter4z.http._
import twitter4z.objects._
import twitter4z.json._

trait Tweets {

  import Param._

  def retweetedBy(id: ID, count: Param[Int] = Default, page: Param[Int] = Default)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[List[User]](get, "statuses/" + id + "/retweeted_by", tokens, Count(count), Page(page))

  def retweetedByIds(id: ID, count: Param[Int] = Default, page: Param[Int] = Default, stringifyIds: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[List[ID]](get, "statuses/" + id + "retweeted_by/ids", tokens, Count(count), Page(page), StringifyIds(stringifyIds))

  def retweets(id: ID, count: Param[Int] = Default, page: Param[Int] = Default, includeEntities: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[List[Status]](get, "statuses/retweets/" + id, tokens, Count(count), Page(page), IncludeEntities(includeEntities))

  def show(id: ID, trimUser: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default)(implicit timeout: Timeout) = resource[Status](get, "statuses/show/" + id, None, TrimUser(trimUser), IncludeEntities(includeEntities))

  def destroy(id: ID, trimUser: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Status](post, "statuses/destroy/" + id, tokens, TrimUser(trimUser), IncludeEntities(includeEntities))

  def retweet(id: ID, trimUser: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Status](post, "statuses/retweet/" + id, tokens, TrimUser(trimUser), IncludeEntities(includeEntities))

  def update(status: String, inReplyToStatusId: Param[ID] = Default, location: Param[Location] = Default, placeId: Param[String] = Default, displayCoordinates: Param[Boolean] = Default, trimUser: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default, wrapLinks: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Status](post, "statuses/update", tokens, List(Status(New(status)), InReplyToStatusId(inReplyToStatusId), PlaceId(placeId), DisplayCoordinates(displayCoordinates), TrimUser(trimUser), IncludeEntities(includeEntities), WrapLinks(wrapLinks)) ::: location.listr.flatMap(_.list): _*)

}
