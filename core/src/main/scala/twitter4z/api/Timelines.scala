package twitter4z.api

import twitter4z.objects.{ JSON, Status }
import parameters.Paging

import dispatch._

import org.specs2.html._

trait Timelines { self: API =>

  val Statuses = TwitterAPI / "1/statuses"

  case class HomeTimeline(parameters: Parameters = Default) extends Resource[List[Status], Required, HomeTimeline](Statuses / "home_timeline.json" <<?) with Paging {
    def apply(parameters: Parameters) = HomeTimeline(parameters)
  }

  lazy val homeTimeline = HomeTimeline()

  case class Mentions(parameters: Parameters = Default) extends Resource[List[Status], Required, Mentions](Statuses / "mentions.json" <<?) with Paging {
    def apply(parameters: Parameters) = Mentions(parameters)
  }

  lazy val mentions = Mentions()

  case class PublicTimeline(parameters: Parameters = Default) extends Resource[List[Status], Optional, PublicTimeline](Statuses / "public_timeline.json" <<?) {
    def apply(parameters: Parameters) = PublicTimeline(parameters)
  }

  lazy val publicTimeline = PublicTimeline()

  case class RetweetedByMe(parameters: Parameters = Default) extends Resource[List[Status], Required, RetweetedByMe](Statuses / "retweeted_by_me.json" <<?) with Paging {
    def apply(parameters: Parameters) = RetweetedByMe(parameters)
  }

  lazy val retweetedByMe = RetweetedByMe()

  case class RetweetedToMe(parameters: Parameters = Default) extends Resource[List[Status], Required, RetweetedToMe](Statuses / "retweeted_to_me.json" <<?) with Paging {
    def apply(parameters: Parameters) = RetweetedToMe(parameters)
  }

  lazy val retweetedToMe = RetweetedToMe()

  case class RetweetsOfMe(parameters: Parameters = Default) extends Resource[List[Status], Required, RetweetsOfMe](Statuses / "retweets_of_me.json" <<?) with Paging {
    def apply(parameters: Parameters) = RetweetsOfMe(parameters)
  }

  lazy val retweetsOfMe = RetweetsOfMe()

  case class UserTimeline(parameters: Parameters = Default) extends Resource[List[Status], Required, UserTimeline](Statuses / "retweets_of_me.json" <<?) with Paging {
    def apply(parameters: Parameters) = UserTimeline(parameters)
  }

  lazy val userTimeline = UserTimeline()

  case class RetweetedToUser(parameters: Parameters = Default)(id: Long) extends Resource[List[Status], Required, RetweetedToUser](Statuses / "retweeted_to_user.json" <<?) with Paging {
    def apply(parameters: Parameters) = RetweetedToUser(parameters)(id)
  }

  lazy val retweetedToUser = RetweetedToUser()_

  case class RetweetedByUser(parameters: Parameters = Default)(id: Long) extends Resource[List[Status], Required, RetweetedByUser](Statuses / "retweeted_by_user.json" <<?) with Paging {
    def apply(parameters: Parameters) = RetweetedByUser(parameters)(id)
  }

  lazy val retweetedByUser = RetweetedByUser()_

}
