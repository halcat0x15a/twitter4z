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

  def readTokens(stream: FileInputStream): Tokens = withResource(new ObjectInputStream(stream), (_: ObjectInputStream).readObject.asInstanceOf[Tokens])

  def readTokens(file: File): Tokens = readTokens(new FileInputStream(file))

  def readTokens(name: String): Tokens = readTokens(new FileInputStream(name))

  def writeTokens(stream: FileOutputStream): Tokens => Unit = (tokens: Tokens) => withResource(new ObjectOutputStream(stream), (_: ObjectOutputStream).writeObject(tokens))

  def writeTokens(file: File): Tokens => Unit = writeTokens(new FileOutputStream(file))

  def writeTokens(name: String): Tokens => Unit = writeTokens(new FileOutputStream(name))

}
