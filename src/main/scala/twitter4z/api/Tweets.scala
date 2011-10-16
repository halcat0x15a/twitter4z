package twitter4z.api

import scalaz._
import Scalaz._
import twitter4z.http._
import twitter4z.objects._
import twitter4z.json._

trait Tweets {

  def retweetedBy(id: JLong, count: JInt = null, page: JInt = null)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[List[User]](get, "statuses/" + id + "/retweeted_by", tokens, Count(count), Page(page))

  def retweetedByIds(id: JLong, count: JInt = null, page: JInt = null, stringifyIds: JBoolean = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[List[ID]](get, "statuses/" + id + "retweeted_by/ids", tokens, Count(count), Page(page), StringifyIds(stringifyIds))

  def retweets(id: JLong, count: JInt = null, page: JInt = null, includeEntities: JBoolean = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[List[Status]](get, "statuses/retweets/" + id, tokens, Count(count), Page(page), IncludeEntities(includeEntities))

  def show(id: JLong, trimUser: JBoolean = null, includeEntities: JBoolean = null)(implicit timeout: Timeout) = resource[Status](get, "statuses/show/" + id, None, TrimUser(trimUser), IncludeEntities(includeEntities))

  def destroy(id: JLong, trimUser: JBoolean = null, includeEntities: JBoolean = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Status](post, "statuses/destroy/" + id, tokens, TrimUser(trimUser), IncludeEntities(includeEntities))

  def retweet(id: JLong, trimUser: JBoolean = null, includeEntities: JBoolean = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Status](post, "statuses/retweet/" + id, tokens, TrimUser(trimUser), IncludeEntities(includeEntities))

  def update(status: String, inReplyToStatusId: JLong = null, location: Location = null, placeId: String = null, displayCoordinates: JBoolean = null, trimUser: JBoolean = null, includeEntities: JBoolean = null, wrapLinks: JBoolean = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Status](post, "statuses/update", tokens, List(Status(status), InReplyToStatusId(inReplyToStatusId), PlaceId(placeId), DisplayCoordinates(displayCoordinates), TrimUser(trimUser), IncludeEntities(includeEntities), WrapLinks(wrapLinks)) ::: Location(location): _*)

}
