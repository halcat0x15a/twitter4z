package twitter4z.api

import java.net.HttpURLConnection
import scalaz._
import Scalaz._
import twitter4z.exception.{ TwitterResult, TwitterNumberFormatException }

case class RateLimit(limit: Int, remaining: Int, reset: Long)

object RateLimit {

  type Result[A] = ValidationNEL[NumberFormatException, A]

  lazy val limit: HttpURLConnection => Result[Int] = _.getHeaderField("X-RateLimit-Limit").parseInt.toValidationNel

  lazy val remaining: HttpURLConnection => Result[Int] = _.getHeaderField("X-RateLimit-Remaining").parseInt.toValidationNel

  lazy val reset: HttpURLConnection => Result[Long] = _.getHeaderField("X-RateLimit-Reset").parseLong.toValidationNel

  lazy val fields = reset &&& (remaining &&& limit)

  lazy val validation: HttpURLConnection => Result[RateLimit] = fields(_).fold(_ <*> _.fold(_ <*> _.map((RateLimit.apply _).curried)))

}
