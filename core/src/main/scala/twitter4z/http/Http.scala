package twitter4z.http

import java.io._
import scalaz._
import Scalaz._
import scalaj.http._

trait Http {

  type Tokens = twitter4z.http.Tokens

  implicit val DefaultTokens: OptionTokens = NoneTokens

  implicit def TokensToSomeTokens(tokens: Tokens): OptionTokens = SomeTokens(tokens)

  implicit def ObjectInputStreamResource = resource[ObjectInputStream](_.close)

  implicit def ObjectOutputStreamResource = resource[ObjectOutputStream](_.close)

  def readTokens(stream: FileInputStream) = {
    val eval = { stream: ObjectInputStream =>
      stream.readObject.asInstanceOf[Tokens]
    }
    withResource(new ObjectInputStream(stream), eval)
  }

  def readTokens(file: File): Tokens = readTokens(new FileInputStream(file))

  def readTokens(name: String): Tokens = readTokens(new FileInputStream(name))

  def writeTokens(stream: FileOutputStream)(tokens: Tokens) {
    val eval = { stream: ObjectOutputStream =>
      stream.writeObject(tokens)
    }
    withResource(new ObjectOutputStream(stream), eval)
  }

  def writeTokens(file: File): Tokens => Unit = writeTokens(new FileOutputStream(file))_

  def writeTokens(name: String): Tokens => Unit = writeTokens(new FileOutputStream(name))_

}
