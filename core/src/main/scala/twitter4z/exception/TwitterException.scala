package twitter4z.exception

import scalaj.http.HttpException

import scalaz._
import Scalaz._

import net.liftweb.json.scalaz.JsonScalaz._

import twitter4z.api.RateLimit

sealed trait TwitterException extends Exception {

  type Exception

  val value: Exception

  override def toString = value.toString

}

case class TwitterHttpException(value: HttpException) extends TwitterException {

  type Exception = HttpException

}

case class TwitterJsonError(value: Error) extends TwitterException {

  type Exception = Error

}

case class TwitterNumberFormatException(value: NumberFormatException) extends TwitterException {

  type Exception = NumberFormatException

}

trait TwitterExceptionInstances {

  implicit val twitterExceptionInstance = new Show[TwitterException] {
    def show(e: TwitterException) = {
      val string = e.value match {
	case throwable: Throwable => throwable.getMessage
	case other => other.toString
      }
      string.toList
    }
  }

}
