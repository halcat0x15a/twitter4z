package twitter4z.http

import java.io._

import scalaj.http.{ Http => ScalajHttp, _ }

import scalaz._
import Scalaz._
import scalaz.concurrent._

trait Http {

  implicit val DefaultTokens: Option[Tokens] = None

  implicit val DefaultTimeout: Int = 1000 * 30

  implicit def TokensToSomeTokens(tokens: Tokens): Option[Tokens] = Some(tokens)

  implicit def ObjectInputStreamResource = resource[ObjectInputStream](_.close)

  implicit def ObjectOutputStreamResource = resource[ObjectOutputStream](_.close)

  private def readTokens(stream: FileInputStream): Tokens = withResource(new ObjectInputStream(stream), (_: ObjectInputStream).readObject.asInstanceOf[Tokens])

  def readTokens(file: File): Tokens = readTokens(new FileInputStream(file))

  def readTokens(name: String): Tokens = readTokens(new FileInputStream(name))

  private def writeTokens(stream: FileOutputStream): Tokens => Unit = (tokens: Tokens) => withResource(new ObjectOutputStream(stream), (_: ObjectOutputStream).writeObject(tokens))

  def writeTokens(file: File): Tokens => Unit = writeTokens(new FileOutputStream(file))

  def writeTokens(name: String): Tokens => Unit = writeTokens(new FileOutputStream(name))

  private def method(method: Method)(implicit timeout: Int) = (url: String) => (HttpOptions.connTimeout _ &&& HttpOptions.readTimeout _).apply(timeout).fold(method(url).options(_, _))

  lazy val get = method(ScalajHttp.apply)

  lazy val post = method(ScalajHttp.post)

}
