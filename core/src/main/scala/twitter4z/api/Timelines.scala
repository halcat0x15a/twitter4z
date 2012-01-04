package twitter4z.api

import scalaz._
import Scalaz._

import twitter4z.objects.Status

import twitter4z.http._

import twitter4z.objects.JSON

trait Timelines { self: HTTP with JSON with API =>

  def homeTimeline(paging: Paging = Paging())(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/home_timeline.json", tokens, paging: _*)

  def mentions(paging: Paging = Paging())(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/mentions.json", tokens, paging: _*)

  def publicTimeline(implicit tokens: OptionalTokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/public_timeline.json", tokens)

  def retweetedByMe(paging: Paging = Paging())(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweeted_by_me.json", tokens, paging: _*)

  def retweetedToMe(paging: Paging = Paging())(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweeted_to_me.json", tokens, paging: _*)

  def retweetsOfMe(paging: Paging = Paging())(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweets_of_me.json", tokens, paging: _*)

  def userTimeline(id: Optional[ID] = Default, paging: Paging = Paging())(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweets_of_me.json", tokens, (id.option.map(_.value) +>:  paging.value): _*)

}
