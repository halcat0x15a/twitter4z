package twitter4z.exception

import scalaj.http.HttpException

import scalaz._
import Scalaz._

import net.liftweb.json.scalaz.JsonScalaz._

import twitter4z.api.RateLimit

sealed trait TwitterException extends Exception {

  type Exception

  val exception: Exception

}

object TwitterException {

  def apply[E, A](f: E => TwitterException): ValidationNEL[E, A] => TwitterResult[A] = _.fail.map2(f).validation

}

case class TwitterHttpException(exception: HttpException) extends TwitterException {

  type Exception = HttpException

}

case class TwitterJsonError(exception: Error) extends TwitterException {

  type Exception = Error

}

object TwitterJsonError {

  def lift[A]: Result[A] => TwitterResult[A] = TwitterException(TwitterJsonError.apply)

}

case class TwitterNumberFormatException(exception: NumberFormatException) extends TwitterException {

  type Exception = NumberFormatException

}

object TwitterNumberFormatException {

  lazy val lift: RateLimit.Result[RateLimit] => TwitterResult[RateLimit] = TwitterException(TwitterNumberFormatException.apply)

}

trait TwitterExceptionInstances {

  implicit lazy val TwitterExceptionShow: Show[TwitterException] = shows {
    _.exception match {
      case throwable: Throwable => throwable.getMessage
      case other => other.toString
    }
  }

}
