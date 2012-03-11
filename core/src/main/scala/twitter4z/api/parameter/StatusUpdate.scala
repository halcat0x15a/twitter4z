package twitter4z.api.parameter

trait StatusUpdate { self: Parameter[StatusUpdate] =>
  lazy val status = apply[String](STATUS)
  lazy val inReplyToStatusId = apply[Long](IN_REPLY_TO_STATUS_ID)
  def location(lat: Double, long: Double) = {
    (this(LAT) = lat)(LONG) = long
  }
  lazy val placeId = apply[String](PLACE_ID)
  lazy val displayCoordinates = apply[String](DISPLAY_COORDINATES)
}
