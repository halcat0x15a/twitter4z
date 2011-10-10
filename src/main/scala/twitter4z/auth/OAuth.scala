package twitter4z.auth

import scalaj.http._
import twitter4z.http._

trait OAuth {

  def requestToken(key: String, secret: String)(implicit timeout: Timeout) = {
    val consumer = Token(key, secret)
    Tokens(consumer, post("""http://api.twitter.com/oauth/request_token""").oauth(consumer).asToken)
  }

  def authorization(tokens: Tokens): String = authorization(tokens.token)

  def authorization(requestToken: Token): String = """http://api.twitter.com/oauth/authorize?oauth_token=""" + requestToken.key

  def accessToken(tokens: Tokens, verifier: String)(implicit timeout: Timeout) = tokens.copy(token = get("""https://api.twitter.com/oauth/access_token""").oauth(tokens.consumer, tokens.token, verifier).asToken)
    

}