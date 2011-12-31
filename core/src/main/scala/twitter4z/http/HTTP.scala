package twitter4z.http

import java.io._

import scala.sys.props

import scalaj.http.{ Http, HttpOptions, Token }

import scalaz._
import Scalaz._
import scalaz.concurrent._

trait HTTP {

  implicit lazy val DefaultTokens: Option[Tokens] = None

  implicit lazy val DefaultTimeout: Int = 1000 * 30

  type Tokens = twitter4z.http.Tokens

  def Tokens(consumer: Token, token: Token) = twitter4z.http.Tokens(consumer, token)

  type Token = scalaj.http.Token

  def Token(key: String, secret: String) = scalaj.http.Token(key, secret)

  lazy val loadProps: Option[Tokens] = {println(props);for {
    key <- props.get("twitter4z.consumer_key")
    secret <- props.get("twitter4z.consumer_secret")
    token <- props.get("twitter4z.access_token")
    tokenSecret <- props.get("twitter4z.access_token_secret")
  } yield Tokens(Token(key, secret), Token(token, tokenSecret))}

  implicit def TokensToSomeTokens(tokens: Tokens): Option[Tokens] = Some(tokens)

  implicit def ObjectInputStreamResource = resource[ObjectInputStream](_.close)

  implicit def ObjectOutputStreamResource = resource[ObjectOutputStream](_.close)

  def readTokens(stream: InputStream): Tokens = withResource(new ObjectInputStream(stream), (_: ObjectInputStream).readObject.asInstanceOf[Tokens])

  def readTokens(file: File): Tokens = readTokens(new FileInputStream(file))

  def readTokens(name: String): Tokens = readTokens(new FileInputStream(name))

  def writeTokens(stream: OutputStream): Tokens => Unit = (tokens: Tokens) => withResource(new ObjectOutputStream(stream), (_: ObjectOutputStream).writeObject(tokens))

  def writeTokens(file: File): Tokens => Unit = writeTokens(new FileOutputStream(file))

  def writeTokens(name: String): Tokens => Unit = writeTokens(new FileOutputStream(name))

  private def method(method: Method)(implicit timeout: Int) = (url: String) => (HttpOptions.connTimeout _ &&& HttpOptions.readTimeout _).apply(timeout).fold(method(url).options(_, _))

  lazy val get = method(Http.apply)

  lazy val post = method(Http.post)

}
