package twitter4z.api

import org.specs2._

import twitter4z.Twitter._

class TimelinesSpec extends Specification { def is =

  args(sequential=true, stopOnFail=true) ^ 
  "Timelines Resource Spec"                              ^
                                                         p^
  "statuses/home_timeline should"                        ^
    "contain 1 status when set 1 for count"              ! HomeTimeline.e1^
    "be empty when since_id is equal to max_id"          ! HomeTimeline.e2^
                                                         p^
  "statuses/mentions should"                             ^
    "contain 1 status when set 1 for count"              ! Mentions.e1^
    "be empty when since_id is equal to max_id"          ! Mentions.e2^
                                                         p^
  "statuses/public_timeline should"                      ^
    "contain 20 statuses"                                ! PublicTimeline.e1^
                                                         p^
  "statuses/retweeted_by_me should"                      ^
    "contain 1 status when set 1 for count"              ! RetweetedByMe.e1^
    "be empty when since_id is equal to max_id"          ! RetweetedByMe.e2^
                                                         p^
  "statuses/retweeted_to_me should"                      ^
    "contain 1 status when set 1 for count"              ! RetweetedToMe.e1^
    "be empty when since_id is equal to max_id"          ! RetweetedToMe.e2^
                                                         p^
  "statuses/retweets_of_me should"                      ^
    "contain 1 status when set 1 for count"              ! RetweetsOfMe.e1^
    "be empty when since_id is equal to max_id"          ! RetweetsOfMe.e2^
                                                         p^
  "statuses/user_timeline should"                        ^
    "contain 1 status when set 1 for count"              ! UserTimeline.e1^
    "be empty when since_id is equal to max_id"          ! UserTimeline.e2^
                                                         end

  implicit lazy val tokens = readTokens(getClass.getResourceAsStream("/test.tokens"))

  case object HomeTimeline {
    lazy val statuses1 = homeTimeline(Paging(count=1)).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = homeTimeline(Paging(sinceId=id, maxId=id)).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object Mentions {
    lazy val statuses1 = mentions(Paging(count=1)).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = mentions(Paging(sinceId=id, maxId=id)).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object PublicTimeline {
    lazy val statuses1 = publicTimeline.unsafe
    lazy val e1 = statuses1 must have size 20
  }

  case object RetweetedByMe {
    lazy val statuses1 = retweetedByMe(Paging(count=1)).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = retweetedByMe(Paging(sinceId=id, maxId=id)).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object RetweetedToMe {
    lazy val statuses1 = retweetedToMe(Paging(count=1)).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = retweetedToMe(Paging(sinceId=id, maxId=id)).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object RetweetsOfMe {
    lazy val statuses1 = retweetsOfMe(Paging(count=1)).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = retweetsOfMe(Paging(sinceId=id, maxId=id)).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object UserTimeline {
    lazy val statuses1 = userTimeline(ID("halcat0x15a"), Paging(count=1)).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = userTimeline(paging=Paging(sinceId=id, maxId=id)).unsafe
    lazy val e2 = statuses2 must be empty
  }

}
