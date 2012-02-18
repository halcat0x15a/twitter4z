package twitter4z

import scalaz._
import Scalaz._

import scalaj.http.Http

package object http {

  type Method = String => Http.Request

  type Token[T <: TokenType] = scalaj.http.Token @@ T

  def Token[T <: TokenType](token: scalaj.http.Token): Token[T] = Tag(token)

  def Token[T <: TokenType](key: String, secret: String): Token[T] = Token(scalaj.http.Token(key, secret))

}
