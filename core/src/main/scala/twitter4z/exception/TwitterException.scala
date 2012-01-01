package twitter4z.exception

import scalaj.http.HttpException

import net.liftweb.json.scalaz.JsonScalaz

sealed trait TwitterException extends Throwable {

  type Exception

  val exception: Exception

}

case class TwitterHttpException(exception: HttpException) extends TwitterException {

  type Exception = HttpException

}

case class TwitterJsonError(exception: JsonScalaz.Error) extends TwitterException {

  type Exception = JsonScalaz.Error

}

case class TwitterNumberFormatException(exception: NumberFormatException) extends TwitterException {

  type Exception = NumberFormatException

}
