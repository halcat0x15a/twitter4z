package twitter4z.exception

import scalaj.http.HttpException

import net.liftweb.json.scalaz.JsonScalaz

sealed trait TwitterException

case class TwitterHttpException(e: HttpException) extends TwitterException

case class TwitterError(e: JsonScalaz.Error) extends TwitterException

case class TwitterNumberFormatException(e: NumberFormatException) extends TwitterException
