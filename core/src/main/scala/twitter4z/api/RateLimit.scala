package twitter4z.api

import java.net.HttpURLConnection
import scalaz._
import Scalaz._
import twitter4z.exception.{ TwitterResult, TwitterNumberFormatException }

case class RateLimit(limit: Int, remaining: Int, reset: Long)

object RateLimit {

  type Result[A] = ValidationNEL[Exception, A]

  def get[A](key: String)(f: String => Validation[NumberFormatException, A]): Map[String, Seq[String]] => Result[A] = _.get(key).flatMap(_.headOption).toSuccess(new NoSuchElementException).flatMap(f).liftFailNel

  lazy val limit = get("X-RateLimit-Limit")(_.parseInt)

  lazy val remaining = get("X-RateLimit-Remaining")(_.parseInt)

  lazy val reset = get("X-RateLimit-Reset")(_.parseLong)

  lazy val fields = reset &&& (remaining &&& limit)

  lazy val validation = fields >>> (_.fold(_ <*> _.fold(_ <*> _.map((RateLimit.apply _).curried))))

}
