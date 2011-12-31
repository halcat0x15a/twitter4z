package twitter4z.api

import twitter4z.objects.Status

import twitter4z.http.HTTP

import twitter4z.json.JSON

trait Timelines { self: HTTP with JSON with API =>

  def homeTimeline(count: Optional[Int] = Default, since_id: Optional[Int] = Default, max_id: Optional[Int] = Default, page: Optional[Int] = Default)(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/home_timeline.json", tokens, "count" -> count, "since_id" -> since_id, "max_id" -> max_id, "page" -> page)

}
