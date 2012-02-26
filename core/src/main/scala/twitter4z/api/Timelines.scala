package twitter4z.api

import twitter4z.objects.{ JSON, Status }
import parameters.Paging

trait Timelines { self: JSON with API =>

  case object homeTimeline extends Resource[List[Status], Required]("GET", "http://api.twitter.com/1/statuses/home_timeline.json") with Paging {
    type Self = homeTimeline.type
    val self = this
  }

  case object mentions extends Resource[List[Status], Required]("GET", "http://api.twitter.com/1/statuses/mentions.json") with Paging {
    type Self = mentions.type
    val self = this
  }

  case object publicTimeline extends Resource[List[Status], Optional]("GET", "http://api.twitter.com/1/statuses/public_timeline.json") {
    type Self = publicTimeline.type
    val self = this
  }

  case object retweetedByMe extends Resource[List[Status], Required]("GET", "http://api.twitter.com/1/statuses/retweeted_by_me.json") with Paging {
    type Self = retweetedByMe.type
    val self = this
  }

  case object retweetedToMe extends Resource[List[Status], Required]("GET", "http://api.twitter.com/1/statuses/retweeted_to_me.json") with Paging {
    type Self = retweetedToMe.type
    val self = this
  }

  case object retweetsOfMe extends Resource[List[Status], Required]("GET", "http://api.twitter.com/1/statuses/retweets_of_me.json") with Paging {
    type Self = retweetsOfMe.type
    val self = this
  }

  case object userTimeline extends Resource[List[Status], Required]("GET", "http://api.twitter.com/1/statuses/retweets_of_me.json") with Paging {
    type Self = userTimeline.type
    val self = this
  }

  case class retweetedToUser(id: Long) extends Resource[List[Status], Required]("GET", "http://api.twitter.com/1/statuses/retweeted_to_user.json") with Paging {
    type Self = retweetedToUser
    val self = this
  }

  case class retweetedByUser(id: Long) extends Resource[List[Status], Required]("GET", "http://api.twitter.com/1/statuses/retweeted_by_user.json") with Paging {
    type Self = retweetedByUser
    val self = this
  }

}
