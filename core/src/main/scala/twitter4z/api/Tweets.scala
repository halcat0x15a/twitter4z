package twitter4z.api

import scalaz._
import Scalaz._

import twitter4z.objects.Status
import twitter4z.objects.User

import twitter4z.http._

import twitter4z.objects.JSON

trait Tweets { self: HTTP with JSON with API =>

  def retweetedBy(id: Long, paging: Paging = Paging())(implicit tokens: OptionalTokens) = resource[List[User]](get, "http://api.twitter.com/1/statuses/%s/retweeted_by.json".format(id), tokens, paging: _*)

  def retweetedByIds(id: Long, paging: Paging = Paging())(implicit tokens: Tokens) = resource[List[Long]](get, "http://api.twitter.com/1/statuses/%s/retweeted_by/ids.json".format(id), tokens, paging: _*)

  def retweets(id: Long, count: Optional[Int] = Default)(implicit tokens: Tokens) = resource[List[Status]](get, "http://api.twitter.com/1/statuses/retweets/%s.json".format(id), tokens, count(Parameters.Count))

  def showStatus(id: Long)(implicit tokens: OptionalTokens) = resource[Status](get, "http://api.twitter.com/1/statuses/show/%s.json".format(id), tokens)

}
