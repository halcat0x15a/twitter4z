package twitter4z

import java.io._
import scalaz._
import Scalaz._
import twitter4z.http._
import twitter4z.api._
import twitter4z.auth._
import twitter4z.objects._

object Twitter4z extends OAuth with Timelines with Tweets {

  implicit val DefaultTimeout = Timeout(3000, 5000)

  implicit val DefaultTokens = none[Tokens]

  implicit def IdToUserId(id: ID) = id.right[String].some

  implicit def StringToUserId(string: String) = string.left[ID].some

  def readTokens(name: String) = {
    val stream = new ObjectInputStream(new FileInputStream(name))
    val tokens = stream.readObject.asInstanceOf[Tokens]
    stream.close()
    Some(tokens)
  }

}
