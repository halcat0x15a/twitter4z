package twitter4z

import scalaz._
import Scalaz._

import java.io._

import twitter4z.exception.TwitterExceptionInstances
import twitter4z.http._
import twitter4z.objects.JSON
import twitter4z.auth._
import twitter4z.api.API

class Twitter[A <: Authentication](val tokens: A) extends HTTP with OAuth with JSON with API {

  type Tokens = A

  override def get: Method = super[HTTP].get

  override def post: Method = super[HTTP].post

  def readObject[A](stream: InputStream): A = {
    val ois = new ObjectInputStream(stream)
    val token = ois.readObject.asInstanceOf[A]
    ois.close()
    token
  }

  def readObject[A](file: File): A = readObject[A](new FileInputStream(file))

  def readObject[A](name: String): A = readObject[A](new FileInputStream(name))

  def writeObject[A](access: A, stream: OutputStream): Unit = {
    val oos = new ObjectOutputStream(stream)
    oos.writeObject(access)
    oos.close()
  }

  def writeObject[A](access: A, file: File): Unit = writeObject(access, new FileOutputStream(file))

  def writeObject[A](access: A, name: String): Unit = writeObject(access, new FileOutputStream(name))

}

object Twitter extends Twitter(Optional)
