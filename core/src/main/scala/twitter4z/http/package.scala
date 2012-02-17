package twitter4z

import scalaj.http.{ Http, HttpOptions }

import scalaz._
import Scalaz._

import twitter4z.exception._
import twitter4z.Twitter._

package object http {

  type Token[T <: TokenType] = scalaj.http.Token @@ T

  def Token[T <: TokenType](token: scalaj.http.Token): Token[T] = Tag(token)

  def Token[T <: TokenType](key: String, secret: String): Token[T] = Token(scalaj.http.Token(key, secret))

  type Method = String => Http.Request

  lazy val oauth: (Http.Request, OptionalTokenPair) => Http.Request = {
    case (request, DefaultTokenPair) => request
    case (request, TokenPair(consumer, token)) => request.oauth(consumer, token)
  }

  def options(method: Method)(implicit conn: Int @@ Conn, read: Int @@ Read, tokenPair: OptionalTokenPair): Method = url => method(url).options(HttpOptions.connTimeout(conn), HttpOptions.readTimeout(read))

  def get(implicit conn: Conn, read: Read, tokenPair: OptionalTokenPair): Method = options(Http.apply)

  def post(implicit conn: Conn, read: Read, tokenPair: OptionalTokenPair): Method = options(Http.post)

}
