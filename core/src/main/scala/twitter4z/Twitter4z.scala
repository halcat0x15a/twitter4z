package twitter4z

import scalaz._
import Scalaz._
import twitter4z.http._
import twitter4z.api._
import twitter4z.auth._

object Twitter4z extends OAuth with Parameters with Timelines with Tweets with Search with DirectMessages with FriendsFollowers with Users with SuggestedUsers with Favorites {

  implicit val DefaultTokens = none[Tokens]

}
