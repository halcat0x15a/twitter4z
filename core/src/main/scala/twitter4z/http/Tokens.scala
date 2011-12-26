package twitter4z.http

import scalaz._
import Scalaz._

case class Tokens(consumer: Token, token: Token) extends NewType[(Token, Token)] {

  val value = (consumer, token)

  override def toString = "Tokens@" + hashCode.toHexString

}
