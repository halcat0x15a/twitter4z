package twitter4z.api

import scalaz._
import Scalaz._
import twitter4z.objects._
import twitter4z.http._
import twitter4z.json._
import twitter4z.auth._

trait Timelines { self: Parameters =>

  def homeTimeline(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeRts: IncludeRts = null, includeEntities: IncludeEntities = null, excludeReplies: ExcludeReplies = null, contributorDetails: ContributorDetails = null)(implicit tokens: Some[Tokens]) = resource[Statuses](get, "http://api.twitter.com/1/statuses/home_timeline", tokens, count, sinceId, maxId, page, trimUser, includeRts, includeEntities, excludeReplies, contributorDetails)

  def mentions(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeRts: IncludeRts = null, includeEntities: IncludeEntities = null, contributorDetails: ContributorDetails = null)(implicit tokens: Some[Tokens]) = resource[Statuses](get, "http://api.twitter.com/1/statuses/mentions", tokens, count, sinceId, maxId, page, trimUser, includeRts, includeEntities, contributorDetails)

  def publicTimeline(trimUser: TrimUser = null, includeEntities: IncludeEntities = null) = resource[Statuses](get, "http://api.twitter.com/1/statuses/public_timeline", None, trimUser, includeEntities)

  def retweetedByMe(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit tokens: Some[Tokens]) = resource[Statuses](get, "http://api.twitter.com/1/statuses/retweeted_by_me", tokens, count, sinceId, maxId, page, trimUser, includeEntities)

  def retweetedToMe(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit tokens: Some[Tokens]) = resource[Statuses](get, "http://api.twitter.com/1/statuses/retweeted_to_me", tokens, count, sinceId, maxId, page, trimUser, includeEntities)

  def retweetsOfMe(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit tokens: Some[Tokens]) = resource[Statuses](get, "http://api.twitter.com/1/statuses/retweets_of_me", tokens, count, sinceId, maxId, page, trimUser, includeEntities)

  def userTimeline(id: Id = null, count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeRts: IncludeRts = null, includeEntities: IncludeEntities = null, excludeReplies: ExcludeReplies = null, contributorDetails: ContributorDetails = null)(implicit tokens: Option[Tokens]) = resource[Statuses](get, "http://api.twitter.com/1/statuses/user_timeline", tokens, id, count, sinceId, maxId, page, trimUser, includeRts, includeEntities, excludeReplies, contributorDetails)

  def retweetedToUser(id: Id, count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit tokens: Option[Tokens]) = resource[Statuses](get, "http://api.twitter.com/1/statuses/retweeted_to_user", tokens, id, count, sinceId, maxId, page, trimUser, includeEntities)

  def retweetedByUser(id: Id, count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit tokens: Option[Tokens]) = resource[Statuses](get, "http://api.twitter.com/1/statuses/retweeted_by_user", tokens, id, count, sinceId, maxId, page, trimUser, includeEntities)

}
