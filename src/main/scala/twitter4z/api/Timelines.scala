package twitter4z.api

import scalaz._
import Scalaz._
import twitter4z.objects._
import twitter4z.http._
import twitter4z.json._
import twitter4z.auth._

trait Timelines {

  import Param._

  def homeTimeline(count: Param[Int] = Default, sinceId: Param[ID] = Default, maxId: Param[ID] = Default, page: Param[Int] = Default, trimUser: Param[Boolean] = Default, includeRts: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default, excludeReplies: Param[Boolean] = Default, contributorDetails: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, "statuses/home_timeline", tokens, Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeRts(includeRts), IncludeEntities(includeEntities), ExcludeReplies(excludeReplies), ContributorDetails(contributorDetails))

  def mentions(count: Param[Int] = Default, sinceId: Param[ID] = Default, maxId: Param[ID] = Default, page: Param[Int] = Default, trimUser: Param[Boolean] = Default, includeRts: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default, contributorDetails: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, "statuses/mentions", tokens, Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeRts(includeRts), IncludeEntities(includeEntities), ContributorDetails(contributorDetails))

  def publicTimeline(trimUser: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default)(implicit timeout: Timeout) = resource[Statuses](get, "statuses/public_timeline", None, TrimUser(trimUser), IncludeEntities(includeEntities))

  def retweetedByMe(count: Param[Int] = Default, sinceId: Param[ID] = Default, maxId: Param[ID] = Default, page: Param[Int] = Default, trimUser: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, "statuses/retweeted_by_me", tokens, Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeEntities(includeEntities))

  def retweetedToMe(count: Param[Int] = Default, sinceId: Param[ID] = Default, maxId: Param[ID] = Default, page: Param[Int] = Default, trimUser: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, "statuses/retweeted_to_me", tokens, Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeEntities(includeEntities))

  def retweetsOfMe(count: Param[Int] = Default, sinceId: Param[ID] = Default, maxId: Param[ID] = Default, page: Param[Int] = Default, trimUser: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Some[Tokens]) = resource[Statuses](get, "statuses/retweets_of_me", tokens, Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeEntities(includeEntities))

  def userTimeline(userId: Param[UserID] = Default, count: Param[Int] = Default, sinceId: Param[ID] = Default, maxId: Param[ID] = Default, page: Param[Int] = Default, trimUser: Param[Boolean] = Default, includeRts: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default, excludeReplies: Param[Boolean] = Default, contributorDetails: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[Statuses](get, "statuses/user_timeline", tokens, userId.map(UserID).listr ::: List(Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeRts(includeRts), IncludeEntities(includeEntities), ExcludeReplies(excludeReplies), ContributorDetails(contributorDetails)): _*)

  def retweetedToUser(userId: UserID, count: Param[Int] = Default, sinceId: Param[ID] = Default, maxId: Param[ID] = Default, page: Param[Int] = Default, trimUser: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[Statuses](get, "statuses/retweeted_to_user", tokens, UserID(userId), Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeEntities(includeEntities))

  def retweetedByUser(userId: UserID, count: Param[Int] = Default, sinceId: Param[ID] = Default, maxId: Param[ID] = Default, page: Param[Int] = Default, trimUser: Param[Boolean] = Default, includeEntities: Param[Boolean] = Default)(implicit timeout: Timeout, tokens: Option[Tokens]) = resource[Statuses](get, "statuses/retweeted_by_user", tokens, UserID(userId), Count(count), SinceId(sinceId), MaxId(maxId), Page(page), TrimUser(trimUser), IncludeEntities(includeEntities))

}
