package twitter4z.api

import org.specs2._

import twitter4z.Twitter

class TimelinesSpec extends Specification { def is =

  args(sequential=true, stopOnFail=true)                                          ^
  "Timelines Resource Spec"                                                       ^
                                                                                  p^
  "statuses/home_timeline should"                                                 ^
    "contain a status when set 1 for count"                                       ! HomeTimeline.e1^
    "be empty when since_id is equal to max_id"                                   ! HomeTimeline.e2^
                                                                                  p^
  "statuses/mentions should"                                                      ^
    "contain a status when set 1 for count"                                       ! Mentions.e1^
    "be empty when since_id is equal to max_id"                                   ! Mentions.e2^
                                                                                  p^
  "statuses/public_timeline should"                                               ^
    "contain 20 statuses"                                                         ! PublicTimeline.e1^
                                                                                  p^
  "statuses/retweeted_by_me should"                                               ^
    "contain a status when set 1 for count"                                       ! RetweetedByMe.e1^
    "be empty when since_id is equal to max_id"                                   ! RetweetedByMe.e2^
                                                                                  p^
  "statuses/retweeted_to_me should"                                               ^
    "contain a status when set 1 for count"                                       ! RetweetedToMe.e1^
    "be empty when since_id is equal to max_id"                                   ! RetweetedToMe.e2^
                                                                                  p^
  "statuses/retweets_of_me should"                                                ^
    "contain a status when set 1 for count"                                       ! RetweetsOfMe.e1^
    "be empty when since_id is equal to max_id"                                   ! RetweetsOfMe.e2^
                                                                                  p^/*
  "statuses/user_timeline should"                                                 ^
    "contain a status when set 1 for count"                                       ! UserTimeline.e1^
    "be empty when since_id is equal to max_id"                                   ! UserTimeline.e2^
    "be halcat0x15a statuses when screen_name equal to halcat0x15a"               ! UserTimeline.e3^
                                                                                  p^
  "statuses/retweeted_to_user should"                                             ^
    "contain a status when set 1 for count"                                       ! RetweetedToUser.e1^
    "be empty when since_id is equal to max_id"                                   ! RetweetedToUser.e2^
    "not contain halcat0x15a status when screen_name equal to halcat0x15a"        ! RetweetedToUser.e3^
                                                                                  p^
  "statuses/retweeted_by_user should"                                             ^
    "contain a status when set 1 for count"                                       ! RetweetedByUser.e1^
    "be empty when since_id is equal to max_id"                                   ! RetweetedByUser.e2^
    "be halcat0x15a statuses when screen_name equal to halcat0x15a"               ! RetweetedByUser.e3^*/
                                                                                  end

  lazy val twitter = {
    val prop = new java.util.Properties
    prop.load(getClass.getResourceAsStream("/test.properties"))
    Twitter(prop.getProperty("consumer.key"), prop.getProperty("consumer.secret"), prop.getProperty("access.token"), prop.getProperty("access.token.secret"))
  }

  case object HomeTimeline {
    lazy val statuses1 = twitter.homeTimeline.count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = twitter.homeTimeline.sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object Mentions {
    lazy val statuses1 = twitter.mentions.count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = twitter.mentions.sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object PublicTimeline {
    lazy val statuses1 = twitter.publicTimeline.unsafe
    lazy val e1 = statuses1 must have size 20
  }

  case object RetweetedByMe {
    lazy val statuses1 = twitter.retweetedByMe.count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = twitter.retweetedByMe.sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object RetweetedToMe {
    lazy val statuses1 = twitter.retweetedToMe.count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = twitter.retweetedToMe.sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object RetweetsOfMe {
    lazy val statuses1 = twitter.retweetsOfMe.count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = twitter.retweetsOfMe.sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
  }
/*
  case object UserTimeline {
    lazy val statuses1 = twitter.UserTimeline(ID("halcat0x15a")).count(1)
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = userTimeline(ID("halcat0x15a"), paging=IdPaging(sinceId=id, maxId=id)).unsafe
    lazy val e2 = statuses2 must be empty
    lazy val e3 = forall (statuses1) { status =>
      status.user.screenName must be equalTo "halcat0x15a"
    }
  }

  case object RetweetedToUser {
    lazy val statuses1 = retweetedToUser(ID("halcat0x15a"), IdPaging(count=1)).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = retweetedToUser(ID("halcat0x15a"), paging=IdPaging(sinceId=id, maxId=id)).unsafe
    lazy val e2 = statuses2 must be empty
    lazy val e3 = forall (statuses1) { status =>
      status.user.screenName must not equalTo "halcat0x15a"
    }
  }

  case object RetweetedByUser {
    lazy val statuses1 = retweetedByUser(ID("halcat0x15a"), IdPaging(count=1)).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = retweetedByUser(ID("halcat0x15a"), paging=IdPaging(sinceId=id, maxId=id)).unsafe
    lazy val e2 = statuses2 must be empty
    lazy val e3 = forall (statuses1) { status =>
      status.user.screenName must be equalTo "halcat0x15a"
    }
  }
*/
}
