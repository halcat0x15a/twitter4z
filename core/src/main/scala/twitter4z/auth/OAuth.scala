package twitter4z.auth

import scalaz._
import Scalaz._

import dispatch._
import dispatch.oauth._
import dispatch.twitter.Auth._

import org.specs2.html._

trait OAuth {

  def consumer(key: String, secret: String): Consumer = Consumer(key, secret)

  def requestToken(consumer: Consumer): Token @@ Request = tag(Http(request_token(consumer)))

  def requestToken(consumer: Consumer, callback: String): Token @@ Request = tag(Http(request_token(consumer, callback)))

  def authorizeURI(request: Token @@ Request) = authorize_url(request).to_uri

  def authenticationURI(request: Token @@ Request) = authenticate_url(request).to_uri

  def accessToken(consumer: Consumer, request: Token @@ Request, verifier: String): (Token @@ Access, Long, String) = Http(access_token(consumer, request, verifier)).mapElements(tag.apply, _.toLong, identity)

}
