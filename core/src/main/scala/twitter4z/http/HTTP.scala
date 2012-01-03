package twitter4z.http

import java.io._

import scala.sys.props

import scalaj.http.Http

import scalaz._
import Scalaz._
import scalaz.concurrent._

trait HTTP {

  implicit lazy val DefaultTokens: OptionalTokens = DummyTokens

  implicit lazy val DefaultTimeout: Int = 1000 * 30

  implicit def ObjectInputStreamResource = resource[ObjectInputStream](_.close)

  implicit def ObjectOutputStreamResource = resource[ObjectOutputStream](_.close)

  def readTokens(stream: InputStream): Tokens = withResource(new ObjectInputStream(stream), (_: ObjectInputStream).readObject.asInstanceOf[Tokens])

  def readTokens(file: File): Tokens = readTokens(new FileInputStream(file))

  def readTokens(name: String): Tokens = readTokens(new FileInputStream(name))

  def writeTokens(stream: OutputStream): Tokens => Unit = (tokens: Tokens) => withResource(new ObjectOutputStream(stream), (_: ObjectOutputStream).writeObject(tokens))

  def writeTokens(file: File): Tokens => Unit = writeTokens(new FileOutputStream(file))

  def writeTokens(name: String): Tokens => Unit = writeTokens(new FileOutputStream(name))

}
