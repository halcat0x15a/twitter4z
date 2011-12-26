package twitter4z.api

import java.net.HttpURLConnection
import scalaz._
import Scalaz._
import twitter4z.{ TwitterResult, TwitterNumberFormatException }
import twitter4z.http._

case class RateLimit(limit: Int, remaining: Int, reset: Long)

object RateLimit {

  type Result = ValidationNEL[NumberFormatException, RateLimit]

  def apply(conn: HttpURLConnection): Result = (conn.getHeaderField("X-RateLimit-Limit").parseInt.liftFailNel <***> (conn.getHeaderField("X-RateLimit-Remaining").parseInt.liftFailNel, conn.getHeaderField("X-RateLimit-Reset").parseLong.liftFailNel))(RateLimit.apply)//.fail.map2(TwitterNumberFormatException).validation

}
