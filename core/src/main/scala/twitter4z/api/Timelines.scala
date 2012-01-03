package twitter4z.api

import twitter4z.objects.Status

import twitter4z.http._

import twitter4z.objects.JSON

trait Timelines { self: HTTP with JSON with API =>

  def homeTimeline(paging: Paging = Paging())(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/home_timeline.json", tokens, paging: _*)

  def mentions(paging: Paging = Paging())(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/mentions.json", tokens, paging: _*)

  def publicTimeline(implicit tokens: OptionalTokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/public_timeline.json", tokens)

}
