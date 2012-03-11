package twitter4z.api

import scalaz._
import Scalaz._

import twitter4z.objects.{Status, User}

import parameter._

trait Tweets { self: API =>

  case class RetweetedBy(parameters: Parameters)(id: Long) extends Resource[List[User], Optional, RetweetedBy](STATUSES / id.shows / "retweeted_by.json" <<?) with Paging {
    def apply(parameters: Parameters) = RetweetedBy(parameters)(id)
  }

  lazy val retweetedBy = RetweetedBy(Map.empty)_

  case class RetweetedByIds(parameters: Parameters)(id: Long) extends Resource[List[Long], Required, RetweetedByIds](STATUSES / id.shows / "retweeted_by/ids.json" <<?) with Paging {
    def apply(parameters: Parameters) = RetweetedByIds(parameters)(id)
  }

  lazy val retweetedByIds = RetweetedByIds(Map.empty)_

  case class Retweets(parameters: Parameters)(id: Long) extends Resource[List[Status], Required, Retweets](STATUSES / "retweets" / (id.shows |+| ".json") <<?) with Count {
    def apply(parameters: Parameters) = Retweets(parameters)(id)
  }

  lazy val retweets = Retweets(Map.empty)_

  case class ShowStatus(parameters: Parameters)(id: Long) extends Resource[Status, Optional, ShowStatus](STATUSES / "show" / (id.shows |+| ".json") <<?) {
    def apply(parameters: Parameters) = ShowStatus(parameters)(id)
  }

  lazy val showStatus = ShowStatus(Map.empty)_

  case class DestroyStatus(parameters: Parameters)(id: Long) extends Resource[Status, Required, DestroyStatus](STATUSES / "destroy" / (id.shows |+| ".json") <<) {
    def apply(parameters: Parameters) = DestroyStatus(parameters)(id)
  }

  lazy val destroyStatus = DestroyStatus(Map.empty)_

  case class Retweet(parameters: Parameters)(id: Long) extends Resource[Status, Required, Retweet](STATUSES / "retweet" / (id.shows |+| ".json") <<) {
    def apply(parameters: Parameters) = Retweet(parameters)(id)
  }

  lazy val retweet = Retweet(Map.empty)_

  case class UpdateStatus(parameters: Parameters) extends Resource[Status, Required, UpdateStatus](STATUSES / "update.json" <<) {
    def apply(parameters: Parameters) = UpdateStatus(parameters)
    lazy val status = apply[String](STATUS)
    lazy val inReplyToStatusId = apply[Long](IN_REPLY_TO_STATUS_ID)
    def location(lat: Double, long: Double) = {
      (this(LAT) = lat)(LONG) = long
    }
    lazy val placeId = apply[String](PLACE_ID)
    lazy val displayCoordinates = apply[String](DISPLAY_COORDINATES)
  }

  lazy val updateStatus = UpdateStatus(Map.empty).status

}
