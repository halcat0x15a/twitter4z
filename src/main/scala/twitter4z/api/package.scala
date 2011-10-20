package twitter4z

import scalaz._
import Scalaz._
import scalaj.http._
import net.liftweb.json.scalaz.JsonScalaz._
import http._
import objects._
import json._

package object api {

  val HomeTimeline = "statuses/home_timeline"
  val Mentions = "statuses/mentions"
  val PublicTimeline = "statuses/public_timeline"
  val RetweetedByMe = "statuses/retweeted_by_me"
  val RetweetedToMe = "statuses/retweeted_to_me"
  val RetweetsOfMe = "statuses/retweets_of_me"
  val UserTimeline = "statuses/user_timeline"
  val RetweetedToUser = "statuses/retweeted_to_user"
  val RetweetedByUser = "statuses/retweeted_by_user"

  type Statuses = List[Status]

  def resource[A](method: Method, string: String, tokens: Option[Tokens], optionalParameters: Parameter*)(implicit jsonr: JSONR[A]) = {
    val params = optionalParameters.filter(null !=).map(_.value)
    val request = method("http://api.twitter.com/1/" + string + ".json").params(params: _*)
    fromJSON[A](tokens.fold(request.oauth(_), request)(parse))
  }

}
