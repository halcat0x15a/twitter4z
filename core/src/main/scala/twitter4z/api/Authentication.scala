package twitter4z.api

import java.io._

import dispatch.oauth._

import org.specs2.html._

import twitter4z._
import twitter4z.auth._

sealed trait Authentication

case class Required(consumer: Consumer, token: Token @@ Access) extends Authentication {

  def write(stream: OutputStream): Unit = {
    val out = new ObjectOutputStream(stream)
    out.writeObject(this)
    out.close()
  }

  def write(file: File): Unit = write(new FileOutputStream(file))

  def write(name: String): Unit = write(new FileOutputStream(name))

}

object Required {

  def apply(consumer: Consumer, token: Token @@ Request, verifier: String): Required = {
    Twitter.accessToken(consumer, token, verifier) match {
      case (token, userId, screenName) => Required(consumer, token)
    }
  }

  def apply(consumerKey: String, consumerSecret: String, token: String, secret: String): Required = {
    Required(Consumer(consumerKey, consumerSecret), tag(Token(token, secret)))
  }

  def apply(stream: InputStream): Required = {
    val in = new ObjectInputStream(stream)
    val auth = in.readObject.asInstanceOf[Required]
    in.close()
    auth
  }

  def apply(file: File): Required = Required(new FileInputStream(file))

  def apply(name: String): Required = Required(new FileInputStream(name))

  def apply(props: java.util.Properties): Required = Required(props.getProperty("consumer.key"), props.getProperty("consumer.secret"), props.getProperty("access.token"), props.getProperty("access.token.secret"))

}

sealed trait Optional extends Authentication

case object Optional extends Optional
