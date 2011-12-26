package twitter4z.api

import java.net.HttpURLConnection
import scalaz._
import Scalaz._
import twitter4z.exception.{ TwitterResult, TwitterNumberFormatException }
import twitter4z.http._

case class RateLimit(limit: Int, remaining: Int, reset: Long)

object RateLimit {

  type Result[A] = ValidationNEL[NumberFormatException, A]

  lazy val limit: HttpURLConnection => Result[Int] = _.getHeaderField("X-RateLimit-Limit").parseInt.liftFailNel

  lazy val remaining: HttpURLConnection => Result[Int] = _.getHeaderField("X-RateLimit-Remaining").parseInt.liftFailNel

  lazy val reset: HttpURLConnection => Result[Long] = _.getHeaderField("X-RateLimit-Reset").parseLong.liftFailNel

  def apply(conn: HttpURLConnection): Result[RateLimit] = (limit(conn) <***> (remaining(conn), reset(conn)))(RateLimit.apply)

}
