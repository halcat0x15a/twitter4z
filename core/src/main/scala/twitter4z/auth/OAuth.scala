package twitter4z.auth

import java.io._
import scalaz._
import Scalaz._
import twitter4z.http._

trait OAuth { self: Http =>

  def requestToken(consumer: Token): Tokens = Tokens(consumer, post("http://api.twitter.com/oauth/request_token").oauth(consumer).asToken)

  def requestToken(key: String, secret: String): Tokens = requestToken(Token(key, secret))

  def authorization(tokens: Tokens): String = authorization(tokens.token)

  def authorization(requestToken: Token): String = "http://api.twitter.com/oauth/authorize?oauth_token=" + requestToken.key

  def accessToken(tokens: Tokens, verifier: String) = tokens.copy(token = get("https://api.twitter.com/oauth/access_token").oauth(tokens.consumer, tokens.token, verifier).asToken)

}