package twitter4z.api

import scalaz._
import Scalaz._
import twitter4z.objects._
import twitter4z.http._
import twitter4z.json._
import twitter4z.auth._

trait Timelines {

  def homeTimeline(count: JInt = null, sinceId: JLong = null, maxId: JLong = null, page: JInt = null, trimUser: JBoolean = null, includeRts: JBoolean = null, includeEntities: JBoolean = null, excludeReplies: JBoolean = null, contributorDetails: JBoolean = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, HomeTimeline, tokens, Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeRts(includeRts), IncludeEntities(includeEntities), ExcludeReplies(excludeReplies), ContributorDetails(contributorDetails))

  def mentions(count: JInt = null, sinceId: JLong = null, maxId: JLong = null, page: JInt = null, trimUser: JBoolean = null, includeRts: JBoolean = null, includeEntities: JBoolean = null, contributorDetails: JBoolean = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, Mentions, tokens, Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeRts(includeRts), IncludeEntities(includeEntities), ContributorDetails(contributorDetails))

  def publicTimeline(trimUser: JBoolean = null, includeEntities: JBoolean = null)(implicit timeout: Timeout) = resource[Statuses](get, PublicTimeline, None, TrimUser(trimUser), IncludeEntities(includeEntities))

  def retweetedByMe(count: JInt = null, sinceId: JLong = null, maxId: JLong = null, page: JInt = null, trimUser: JBoolean = null, includeEntities: JBoolean = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, RetweetedByMe, tokens, Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeEntities(includeEntities))

  def retweetedToMe(count: JInt = null, sinceId: JLong = null, maxId: JLong = null, page: JInt = null, trimUser: JBoolean = null, includeEntities: JBoolean = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, RetweetedToMe, tokens, Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeEntities(includeEntities))

  def retweetsOfMe(count: JInt = null, sinceId: JLong = null, maxId: JLong = null, page: JInt = null, trimUser: JBoolean = null, includeEntities: JBoolean = null)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, RetweetsOfMe, tokens, Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeEntities(includeEntities))

  def userTimeline(userId: UserID = null, count: JInt = null, sinceId: JLong = null, maxId: JLong = null, page: JInt = null, trimUser: JBoolean = null, includeRts: JBoolean = null, includeEntities: JBoolean = null, excludeReplies: JBoolean = null, contributorDetails: JBoolean = null)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[Statuses](get, UserTimeline, tokens, UserID(userId), Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeRts(includeRts), IncludeEntities(includeEntities), ExcludeReplies(excludeReplies), ContributorDetails(contributorDetails))

  def retweetedToUser(userId: UserID, count: JInt = null, sinceId: JLong = null, maxId: JLong = null, page: JInt = null, trimUser: JBoolean = null, includeEntities: JBoolean = null)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[Statuses](get, RetweetedToUser, tokens, UserID(userId), Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeEntities(includeEntities))

  def retweetedByUser(userId: UserID, count: JInt = null, sinceId: JLong = null, maxId: JLong = null, page: JInt = null, trimUser: JBoolean = null, includeEntities: JBoolean = null)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[Statuses](get, RetweetedByUser, tokens, UserID(userId), Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeEntities(includeEntities))

}
