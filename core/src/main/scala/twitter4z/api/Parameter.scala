package twitter4z.api

import scalaz._
import Scalaz._

trait Parameter extends NewType[Seq[Option[(String, String)]]]

object Parameters {

  val Count = "count"

  val Page = "page"

  val SinceId = "since_id"

  val MaxId = "max_id"

}
