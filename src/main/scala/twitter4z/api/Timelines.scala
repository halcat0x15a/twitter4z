package twitter4z.api

import scalaz._
import Scalaz._
import twitter4z.objects._
import twitter4z.http._
import twitter4z.json._
import twitter4z.auth._

trait Timelines { self: Parameters =>

  def homeTimeline(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeRts: IncludeRts = null, includeEntities: IncludeEntities = null, excludeReplies: ExcludeReplies = null, contributorDetails: ContributorDetails = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, HomeTimeline, tokens, count, sinceId, maxId, page, trimUser, includeRts, includeEntities, excludeReplies, contributorDetails)

  def mentions(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeRts: IncludeRts = null, includeEntities: IncludeEntities = null, contributorDetails: ContributorDetails = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, Mentions, tokens, count, sinceId, maxId, page, trimUser, includeRts, includeEntities, contributorDetails)

  def publicTimeline(trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit timeout: Timeout) = resource[Statuses](get, PublicTimeline, None, trimUser, includeEntities)

  def retweetedByMe(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, RetweetedByMe, tokens, count, sinceId, maxId, page, trimUser, includeEntities)

  def retweetedToMe(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, RetweetedToMe, tokens, count, sinceId, maxId, page, trimUser, includeEntities)

  def retweetsOfMe(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, RetweetsOfMe, tokens, count, sinceId, maxId, page, trimUser, includeEntities)

  def userTimeline(userId: Either[ScreenName, UserId] = null, count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeRts: IncludeRts = null, includeEntities: IncludeEntities = null, excludeReplies: ExcludeReplies = null, contributorDetails: ContributorDetails = null)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[Statuses](get, UserTimeline, tokens, userId, count, sinceId, maxId, page, trimUser, includeRts, includeEntities, excludeReplies, contributorDetails)

  def retweetedToUser(userId: Either[ScreenName, UserId], count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[Statuses](get, RetweetedToUser, tokens, userId, count, sinceId, maxId, page, trimUser, includeEntities)

  def retweetedByUser(userId: Either[ScreenName, UserId], count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null, trimUser: TrimUser = null, includeEntities: IncludeEntities = null)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[Statuses](get, RetweetedByUser, tokens, userId, count, sinceId, maxId, page, trimUser, includeEntities)

}
