package twitter4z

import scalaz._
import Scalaz._

import java.io._

import dispatch.oauth._

import org.specs2.html._

import twitter4z.auth._
import twitter4z.api._

trait Twitter extends OAuth with API

object Twitter extends Twitter {

  type Auth = Optional

  val auth = Optional

  def apply(required: Required): Twitter = new Twitter {
    type Auth = Required
    val auth = required
  }

  def apply(consumer: Consumer, token: Token @@ twitter4z.auth.Request, verifier: String): Twitter = {
    Twitter(Required(consumer, token, verifier))
  }

  def apply(consumerKey: String, consumerSecret: String, token: String, secret: String): Twitter = {
    Twitter(Required(consumerKey, consumerSecret, token, secret))
  }

  def apply(stream: InputStream): Twitter = {
    Twitter(Required(stream))
  }

  def apply(file: File): Twitter = {
    Twitter(Required(file))
  }

  def apply(name: String): Twitter = {
    Twitter(Required(name))
  }

}
