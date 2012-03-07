package twitter4z.api.parameters

import scalaz._
import Scalaz._

import twitter4z.api._

trait Parameter {

  type Self <: Parameter

  def apply(parameters: Parameters): Self

  val parameters: Parameters

  def update[A: Show](key: String, value: A): Self = {
    apply(parameters + (key -> value.shows))
  }

}

trait Paging { self: Parameter =>
  def count(count: Int) = update("count", count)
  def page(page: Int) = update("page", page)
  def sinceId(sinceId: Long) = update("since_id", sinceId)
  def maxId(maxId: Long) = update("max_id", maxId)
}

trait UserId { self: Parameter =>
  def userId(userId: Long) = update("user_id", userId)
  def screenName(screenName: String) = this("screen_name") = screenName
}

trait Status { self: Parameter =>
  def status(status: String) = this("status") = status
}

trait InReplyToStatusId { self: Parameter =>
  def inReplyToStatusId(inReplyToStatusId: Long) = this("in_reply_to_status_id") = inReplyToStatusId
}

trait Location { self: Parameter =>
  def location(lat: Double, long: Double) = {
    this("lat") = lat
    this("long") = long
  }
}

trait PlaceId { self: Parameter =>
  def placeId(placeId: String) = this("place_id") = placeId
}

trait DisplayCoordinates { self: Parameter =>
  def displayCoordinates(displayCoordinates: String) = this("display_coordinates") = displayCoordinates
}
