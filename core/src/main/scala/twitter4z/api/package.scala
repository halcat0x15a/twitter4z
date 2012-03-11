package twitter4z

import org.specs2.html._
import dispatch._

package object api {

  type Parameters = Map[String, String]

  val API_TWITTER_COM = :/("api.twitter.com") / "1"

  val STATUSES = API_TWITTER_COM / "statuses"

  val DIRECT_MESSAGES = API_TWITTER_COM / "direct_messages"

  val FOLLOWERS = API_TWITTER_COM / "followers"

  val FRIENDS = API_TWITTER_COM / "friends"

  val FRIENDSHIPS = API_TWITTER_COM / "friendships"

  val USERS = API_TWITTER_COM / "users"

  val FAVORITES = API_TWITTER_COM / "favorites"

  val LISTS =  API_TWITTER_COM / "lists"

}
