package twitter4z.api

import org.specs2._

import twitter4z.Twitter._

class TimelinesSpec extends Specification { def is =

  args(sequential=true, stopOnFail=true) ^ 
  "Timelines Resource Spec"                              ^
                                                         p^
  "statuses/home_timeline should"                        ^
    "contain 50 statuses when set 50 for count"          ! HomeTimeline.e1^
    "be empty when since_id is equal to max_id"          ! HomeTimeline.e2^
                                                         p^
  "statuses/mentions should"                             ^
    "contain 10 statuses when set 10 for count"          ! Mentions.e1^
    "be empty when since_id is equal to max_id"          ! Mentions.e2^
                                                         end

  implicit lazy val tokens = readTokens(getClass.getResourceAsStream("/test.tokens"))

  case object HomeTimeline {
    lazy val statuses1 = homeTimeline(count=50).unsafe
    lazy val e1 = statuses1 must have size 50
    lazy val id = statuses1.last.id
    lazy val statuses2 = homeTimeline(since_id=id, max_id=id).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object Mentions {
    lazy val statuses1 = mentions(count=10).unsafe
    lazy val e1 = statuses1 must have size 10
    lazy val id = statuses1.last.id
    lazy val statuses2 = mentions(since_id=id, max_id=id).unsafe
    lazy val e2 = statuses2 must be empty
  }

}
