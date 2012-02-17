package twitter4z.auth

import java.net.URL

import scalaz._
import Scalaz._

import twitter4z.http._

trait OAuth { self: HTTP =>

  def requestToken(consumer: Token[Consumer]): Token[Request] = Token(post("http://api.twitter.com/oauth/request_token").oauth(consumer).asToken)

  def requestToken(key: String, secret: String): Token[Request] = requestToken(Token[Consumer](key, secret))

  def authenticationURL(request: Token[Request]) = new URL("http://api.twitter.com/oauth/authorize?oauth_token=" |+| request.key)

  def accessToken(consumer: Token[Consumer], request: Token[Request], verifier: String): Token[Access] = Token(get("https://api.twitter.com/oauth/access_token").oauth(consumer, request, verifier).asToken)

}
