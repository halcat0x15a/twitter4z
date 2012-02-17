package twitter4z.api

import scalaz._
import Scalaz._

import twitter4z.http._

import twitter4z.objects.JSON

trait Timelines { self: ParameterSyntax with HTTP with JSON with API =>

  import twitter4z.objects.Status

  def homeTimeline(paging: IdPaging = IdPaging())(implicit tokens: TokenPair) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/home_timeline.json", paging.params: _*)

  def mentions(paging: IdPaging = IdPaging())(implicit tokens: TokenPair) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/mentions.json", paging.params: _*)

  def publicTimeline(implicit tokens: OptionalTokenPair) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/public_timeline.json")

  def retweetedByMe(paging: IdPaging = IdPaging())(implicit tokens: TokenPair) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweeted_by_me.json", paging.params: _*)

  def retweetedToMe(paging: IdPaging = IdPaging())(implicit tokens: TokenPair) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweeted_to_me.json", paging.params: _*)

  def retweetsOfMe(paging: IdPaging = IdPaging())(implicit tokens: TokenPair) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweets_of_me.json", paging.params: _*)

  def userTimeline(id: Optional[Id] = Default, paging: IdPaging = IdPaging())(implicit tokens: TokenPair) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweets_of_me.json", (id.map(param[Id]).toList |+|  paging.params): _*)

  def retweetedToUser(id: Id, paging: IdPaging = IdPaging())(implicit tokens: TokenPair) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweeted_to_user.json", (param(id) :: paging.params): _*)

  def retweetedByUser(id: Id, paging: IdPaging = IdPaging())(implicit tokens: TokenPair) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweeted_by_user.json", (param(id) ::  paging.params): _*)

}
