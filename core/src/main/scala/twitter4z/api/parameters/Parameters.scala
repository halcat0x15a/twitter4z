package twitter4z.api.parameters

import scalaz._
import Scalaz._

trait Parameters {

  type Self <: Parameters

  val self: Self

  private var parameter: Map[String, String] = Map.empty

  def update[A: Show](key: String, value: A): Self = {
    parameter += key -> value.shows
    self
  }

  def parameters = parameter.toList

}

trait Count { self: Parameters =>
  def count(count: Int) = update("count", count)
}

trait Page { self: Parameters =>
  def page(page: Int) = update("page", page)
}

trait SinceId { self: Parameters =>
  def sinceId(sinceId: Long) = update("since_id", sinceId)
}

trait MaxId { self: Parameters =>
  def maxId(maxId: Long) = update("max_id", maxId)
}

trait UserId { self: Parameters =>
  def userId(userId: Long) = update("user_id", userId)
}

trait ScreenName { self: Parameters =>
  def screenName(screenName: String) = this("screen_name") = screenName
}

trait Status { self: Parameters =>
  def status(status: String) = this("status") = status
}

trait InReplyToStatusId { self: Parameters =>
  def inReplyToStatusId(inReplyToStatusId: Long) = this("in_reply_to_status_id") = inReplyToStatusId
}

trait Location { self: Parameters =>
  def location(lat: Double, long: Double) = {
    this("lat") = lat
    this("long") = long
  }
}

trait PlaceId { self: Parameters =>
  def placeId(placeId: String) = this("place_id") = placeId
}

trait DisplayCoordinates { self: Parameters =>
  def displayCoordinates(displayCoordinates: String) = this("display_coordinates") = displayCoordinates
}

trait Paging extends Page with Count with SinceId with MaxId { self: Parameters => }
