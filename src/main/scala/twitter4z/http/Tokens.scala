package twitter4z.http

import java.io._
import scalaj.http._

case class Tokens(consumer: Token, token: Token) {

  def write(name: String) = {
    val stream = new ObjectOutputStream(new FileOutputStream(name))
    stream.writeObject(this)
    stream.close()
  }

  override def toString = "Tokens"

}
