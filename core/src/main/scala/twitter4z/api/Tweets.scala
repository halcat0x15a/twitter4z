package twitter4z.api

import scalaz._
import Scalaz._

import twitter4z.objects
import twitter4z.objects.User

trait Tweets { self: API =>
/*
  def retweetedBy(id: Long, paging: Paging = Paging()) = resource[List[User]](get, "http://api.twitter.com/1/statuses/%s/retweeted_by.json".format(id), paging.params: _*)

  def retweetedByIds(id: Long, paging: Paging = Paging())(implicit tokens: TokenPair) = resource[List[Long]](get, "http://api.twitter.com/1/statuses/%s/retweeted_by/ids.json".format(id), paging.params: _*)

  def retweets(id: Long, count: Optional[Int] = Default)(implicit tokens: TokenPair) = resource[List[objects.Status]](get, "http://api.twitter.com/1/statuses/retweets/%s.json".format(id), Params(COUNT, count): _*)

  def showStatus(id: Long) = resource[objects.Status](get, "http://api.twitter.com/1/statuses/show/%s.json".format(id))

	      //def updateStatus(status: Status)(implicit tokens: TokenPair) = resource[objects.Status](get, "http://api.twitter.com/1/statuses/show/%s.json".format(id), status.params: _*)
*/
}
