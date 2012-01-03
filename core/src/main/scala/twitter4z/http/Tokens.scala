package twitter4z.http

import scalaz._
import Scalaz._

sealed trait OptionalTokens

case class Tokens(consumer: ConsumerToken, token: Token) extends NewType[(ConsumerToken, Token)] with OptionalTokens {

  val value = (consumer, token)

  override def toString = "Tokens@" + hashCode.toHexString

}

case object DummyTokens extends OptionalTokens
