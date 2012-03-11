package twitter4z

import scalaz._
import Scalaz._

import java.io._

import dispatch.oauth._

import org.specs2.html._

import twitter4z.auth._
import twitter4z.api._

trait Twitter[A <: Authentication] extends OAuth with API[A]

object Twitter extends Twitter[Optional] {

  val auth = Optional

  def apply(required: Required): Twitter[Required] = new Twitter[Required] {
    val auth = required
  }

  def apply(consumer: Consumer, token: Token @@ twitter4z.auth.Request, verifier: String): Twitter[Required] = {
    Twitter(Required(consumer, token, verifier))
  }

  def apply(consumerKey: String, consumerSecret: String, token: String, secret: String): Twitter[Required] = {
    Twitter(Required(consumerKey, consumerSecret, token, secret))
  }

  def apply(stream: InputStream): Twitter[Required] = {
    Twitter(Required(stream))
  }

  def apply(file: File): Twitter[Required] = {
    Twitter(Required(file))
  }

  def apply(name: String): Twitter[Required] = {
    Twitter(Required(name))
  }

}
