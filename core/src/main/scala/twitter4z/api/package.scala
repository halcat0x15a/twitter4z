package twitter4z

import org.specs2.html._
import dispatch._

package object api {

  type Parameters = Map[String, String]

  val Default: Parameters = Map.empty

  val TwitterAPI = :/("api.twitter.com")

}
