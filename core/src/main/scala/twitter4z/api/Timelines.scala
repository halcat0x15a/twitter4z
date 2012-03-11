package twitter4z.api

import twitter4z.objects.Status
import parameter._

import dispatch._

import org.specs2.html._

trait Timelines { self: API =>

  case class HomeTimeline(parameters: Parameters) extends Resource[List[Status], Required, HomeTimeline](STATUSES / "home_timeline.json" <<?) with Paging {
    def apply(parameters: Parameters) = HomeTimeline(parameters)
  }

  lazy val homeTimeline = HomeTimeline(Map.empty)

  case class Mentions(parameters: Parameters) extends Resource[List[Status], Required, Mentions](STATUSES / "mentions.json" <<?) with Paging {
    def apply(parameters: Parameters) = Mentions(parameters)
  }

  lazy val mentions = Mentions(Map.empty)

  case class PublicTimeline(parameters: Parameters) extends Resource[List[Status], Optional, PublicTimeline](STATUSES / "public_timeline.json" <<?) {
    def apply(parameters: Parameters) = PublicTimeline(parameters)
  }

  lazy val publicTimeline = PublicTimeline(Map.empty)

  case class RetweetedByMe(parameters: Parameters) extends Resource[List[Status], Required, RetweetedByMe](STATUSES / "retweeted_by_me.json" <<?) with Paging {
    def apply(parameters: Parameters) = RetweetedByMe(parameters)
  }

  lazy val retweetedByMe = RetweetedByMe(Map.empty)

  case class RetweetedToMe(parameters: Parameters) extends Resource[List[Status], Required, RetweetedToMe](STATUSES / "retweeted_to_me.json" <<?) with Paging {
    def apply(parameters: Parameters) = RetweetedToMe(parameters)
  }

  lazy val retweetedToMe = RetweetedToMe(Map.empty)

  case class RetweetsOfMe(parameters: Parameters) extends Resource[List[Status], Required, RetweetsOfMe](STATUSES / "retweets_of_me.json" <<?) with Paging {
    def apply(parameters: Parameters) = RetweetsOfMe(parameters)
  }

  lazy val retweetsOfMe = RetweetsOfMe(Map.empty)

  case class UserTimeline(parameters: Parameters) extends Resource[List[Status], Required, UserTimeline](STATUSES / "retweets_of_me.json" <<?) with Paging with UserId {
    def apply(parameters: Parameters) = UserTimeline(parameters)
  }

  lazy val userTimeline = UserTimeline(Map.empty)

  case class RetweetedToUser(parameters: Parameters) extends Resource[List[Status], Required, RetweetedToUser](STATUSES / "retweeted_to_user.json" <<?) with Paging with UserId {
    def apply(parameters: Parameters) = RetweetedToUser(parameters)
  }

  def retweetedToUser[A: ContainsId](value: A) = RetweetedToUser(Map.empty).userId(value)

  case class RetweetedByUser(parameters: Parameters) extends Resource[List[Status], Required, RetweetedByUser](STATUSES / "retweeted_by_user.json" <<?) with Paging with UserId {
    def apply(parameters: Parameters) = RetweetedByUser(parameters)
  }

  def retweetedByUser[A: ContainsId](value: A) = RetweetedByUser(Map.empty).userId(value)

}
