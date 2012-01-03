package twitter4z.http

import scalaz._
import Scalaz._

case class ConsumerToken(value: scalaj.http.Token) extends NewType[scalaj.http.Token]

object ConsumerToken {

  def apply(key: Key, secret: Secret): ConsumerToken = ConsumerToken(scalaj.http.Token(key, secret))

}

case class Token(value: scalaj.http.Token) extends NewType[scalaj.http.Token]

object Token {

  def apply(key: Key, secret: Secret): Token = Token(scalaj.http.Token(key, secret))

}
