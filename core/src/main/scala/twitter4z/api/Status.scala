package twitter4z.api

import scalaz._
import Scalaz._

import Optional._
import Parameters._

case class Status(status: String, inReplyToStatusId: Optional[Long] = Default, location: Optional[(Double, Double)] = Default, placeId: Optional[String] = Default, displayCoordinates: Optional[Boolean] = Default) extends Parameters {

  val locationParams: Params = location.map {
    case (lat, long) => List(Param(LAT, lat), Param(LONG, long))
  }.getOrElse(Nil)

  val params = (STATUS -> status) :: (Params(IN_REPLY_TO_STATUS_ID, inReplyToStatusId) |+| Params(PLACE_ID, placeId) |+| Params(DISPLAY_COORDINATES, displayCoordinates) |+| locationParams)

}
