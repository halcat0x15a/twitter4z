package twitter4z

import scalaz._
import Scalaz._

import java.io._

import dispatch.oauth._

import org.specs2.html._

import twitter4z.auth._
import twitter4z.objects.JSON
import twitter4z.api._

class Twitter[A <: Authentication](val auth: A) extends OAuth with JSON with API {

  type Auth = A

}

object Twitter extends Twitter(Optional) {

  def apply(consumer: Consumer, token: Token @@ twitter4z.auth.Request, verifier: String): Twitter[Required] = {
    accessToken(consumer, token, verifier) match {
      case (token, userId, screenName) => new Twitter(Required(consumer, token))
    }
  }

  def apply(consumerKey: String, consumerSecret: String, token: String, secret: String): Twitter[Required] = {
    new Twitter(Required(Consumer(consumerKey, consumerSecret), tag(Token(token, secret))))
  }

}
