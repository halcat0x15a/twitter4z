package twitter4z.api

import scalaz._
import Scalaz._
import twitter4z.http._
import twitter4z.objects.{ Status => StatusObject, _ }
import twitter4z.json._

trait Tweets { self: Parameters =>

  def retweetedBy(id: Long, count: Count = null, page: Page = null)(implicit tokens: Option[Tokens]) = resource[List[User]](get, "http://api.twitter.com/1/statuses/" + id + "/retweeted_by", tokens, count, page)

  def retweetedByIds(id: Long, count: Count = null, page: Page = null, stringifyIds: StringifyIds = null)(implicit tokens: Some[Tokens]) = resource[List[ID]](get, "http://api.twitter.com/1/statuses/" + id + "retweeted_by/ids", tokens, count, page, stringifyIds)

  def retweets(id: Long, count: Count = null, page: Page = null, includeEntities: IncludeEntities = null)(implicit tokens: Some[Tokens]) = resource[Statuses](get, "http://api.twitter.com/1/statuses/retweets/" + id, tokens, count, page, includeEntities)

  def show(id: Long, trimUser: TrimUser = null, includeEntities: IncludeEntities = null) = resource[StatusObject](get, "http://api.twitter.com/1/statuses/show/" + id, None, trimUser, includeEntities)

  def destroy(id: Long, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit tokens: Some[Tokens]) = resource[StatusObject](post, "http://api.twitter.com/1/statuses/destroy/" + id, tokens, trimUser, includeEntities)

  def retweet(id: Long, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit tokens: Some[Tokens]) = resource[StatusObject](post, "http://api.twitter.com/1/statuses/retweet/" + id, tokens, trimUser, includeEntities)

  def update(status: Status, inReplyToStatusId: InReplyToStatusId = null, location: (Latitude, Longitude) = (null, null), placeId: PlaceId = null, displayCoordinates: DisplayCoordinates = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null, wrapLinks: WrapLinks = null)(implicit tokens: Some[Tokens]) = location match {
    case (lat, long) => resource[StatusObject](post, "http://api.twitter.com/1/statuses/update", tokens, status, inReplyToStatusId, lat, long, placeId, displayCoordinates, trimUser, includeEntities, wrapLinks)
  }

}
