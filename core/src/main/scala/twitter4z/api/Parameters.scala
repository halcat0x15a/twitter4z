package twitter4z.api

import scalaz._
import Scalaz._

trait Parameters {
  val params: Params
}

object Parameters {

  val COUNT = "count"

  val PAGE = "page"

  val SINCE_ID = "since_id"

  val MAX_ID = "max_id"

  val USER_ID = "user_id"

  val SCREEN_NAME = "screen_name"

  val STATUS = "status"

  val IN_REPLY_TO_STATUS_ID = "in_reply_to_status_id"

  val LAT = "lat"

  val LONG = "long"

  val PLACE_ID = "place_id"

  val DISPLAY_COORDINATES = "display_coordinates"

}
