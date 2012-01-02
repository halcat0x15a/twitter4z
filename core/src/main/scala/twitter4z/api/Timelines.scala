package twitter4z.api

import twitter4z.objects.Status

import twitter4z.http.HTTP

import twitter4z.objects.JSON

trait Timelines { self: HTTP with JSON with API =>

  def homeTimeline(count: Optional[Int] = Default, since_id: Optional[Long] = Default, max_id: Optional[Long] = Default, page: Optional[Int] = Default)(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/home_timeline.json", tokens, "count" -> count, "since_id" -> since_id, "max_id" -> max_id, "page" -> page)

  def mentions(count: Optional[Int] = Default, since_id: Optional[Long] = Default, max_id: Optional[Long] = Default, page: Optional[Int] = Default)(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/mentions.json", tokens, "count" -> count, "since_id" -> since_id, "max_id" -> max_id, "page" -> page)

}
