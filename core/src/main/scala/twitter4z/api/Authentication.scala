package twitter4z.api

import java.io._

import dispatch.oauth._

import org.specs2.html._

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

trait AuthenticationFunction {

  def read(stream: InputStream): Required = {
    val in = new ObjectInputStream(stream)
    val auth = in.readObject.asInstanceOf[Required]
    in.close()
    auth
  }

  def read(file: File): Required = read(new FileInputStream(file))

  def read(name: String): Required = read(new FileInputStream(name))

}

sealed trait Optional extends Authentication

case object Optional extends Optional
