package twitter4z.api

import scalaz._
import Scalaz._

case class RateLimit(limit: Int, remaining: Int, reset: Long)

object RateLimit {

  type Result = Validation[NonEmptyList[NumberFormatException], RateLimit]

  def apply(conn: HttpURLConnection): Result = (conn.getHeaderField("X-RateLimit-Limit").parseInt.liftFailNel <***> (conn.getHeaderField("X-RateLimit-Remaining").parseInt.liftFailNel, conn.getHeaderField("X-RateLimit-Reset").parseLong.liftFailNel))(RateLimit.apply)

}
