package twitter4z

import java.io._
import scalaz._
import Scalaz._
import twitter4z.http._
import twitter4z.api._
import twitter4z.auth._
import twitter4z.objects._

object Twitter4z extends OAuth with Timelines with Tweets {

  implicit val DefaultTimeout = Timeout(5000, 5000)

  implicit val DefaultTokens = none[Tokens]

  implicit def IdToUserId(id: ID) = Some(id.right[String])

  implicit def StringToUserId(string: String) = Some(string.left[ID])

  implicit def ToParam[A](value: A) = New(value)

  def readTokens(name: String) = {
    val stream = new ObjectInputStream(new FileInputStream(name))
    val tokens = stream.readObject.asInstanceOf[Tokens]
    stream.close()
    Some(tokens)
  }

}
