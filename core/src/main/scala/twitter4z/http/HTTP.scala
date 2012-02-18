package twitter4z.http

import java.io._

import scalaz._
import Scalaz._

import scalaj.http.{ Http, HttpOptions }

trait HTTP {

  val timeout: Timeout = Timeout(60 * 1000, 60 * 1000)

  def readTokens(stream: InputStream): Token[Access] = {
    val ois = new ObjectInputStream(stream)
    val token = ois.readObject.asInstanceOf[Token[Access]]
    ois.close()
    token
  }

  def readTokens(file: File): Token[Access] = readTokens(new FileInputStream(file))

  def readTokens(name: String): Token[Access] = readTokens(new FileInputStream(name))

  def writeTokens(access: Token[Access], stream: OutputStream): Unit = {
    val oos = new ObjectOutputStream(stream)
    oos.writeObject(access)
    oos.close()
  }

  def writeTokens(access: Token[Access], file: File): Unit = writeTokens(access, new FileOutputStream(file))

  def writeTokens(access: Token[Access], name: String): Unit = writeTokens(access, new FileOutputStream(name))

  private def timeouts(method: Method): Method @@ Timeout = Tag(url => method(url).options(HttpOptions.connTimeout(timeout.conn), HttpOptions.readTimeout(timeout.read)))

  def get = timeouts(Http.apply)

  def post = timeouts(Http.post)

}
