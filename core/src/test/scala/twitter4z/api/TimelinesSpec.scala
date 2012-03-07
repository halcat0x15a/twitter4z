package twitter4z.api

import org.specs2._
import specification._

import twitter4z.Twitter
import twitter4z.api._
import twitter4z.objects._

class TimelinesSpec extends Specification with Twitter { def is =

  args(sequential=true, stopOnFail=true)                                          ^
  "Timelines Resource Spec"                                                       ^
                                                                                  p ^
  "A example for counted memtions"                                                ^
    "Given the following number: ${3}"                                            ^ number ^
    "Then statuses must have size: ${3}"                                          ^ count(mentions) ^
                                                                                  end ^
                                                                                  p ^
  "A example for home timeline since id"                                          ^
    "Given the following id: ${177403983214813185}"                               ^ id ^
    "Then statuses that since id must not contain id"                             ^ since(homeTimeline) ^
							                          end ^
                                                                                  p ^
  "statuses/public_timeline should"                                               ^
    "contain 20 statuses"                                                         ! publictimeline.e ^
                                                                                  p ^
  "statuses/retweeted_by_me should"                                               ^
    "contain a status when set 1 for count"                                       ! ExRetweetedByMe.e1^
    "be empty when since_id is equal to max_id"                                   ! ExRetweetedByMe.e2^
                                                                                  p^
  "statuses/retweeted_to_me should"                                               ^
    "contain a status when set 1 for count"                                       ! ExRetweetedToMe.e1^
    "be empty when since_id is equal to max_id"                                   ! ExRetweetedToMe.e2^
                                                                                  p^
  "statuses/retweets_of_me should"                                                ^
    "contain a status when set 1 for count"                                       ! ExRetweetsOfMe.e1^
    "be empty when since_id is equal to max_id"                                   ! ExRetweetsOfMe.e2^
                                                                                  p^
  "statuses/user_timeline should"                                                 ^
    "contain a status when set 1 for count"                                       ! ExUserTimeline.e1^
    "be empty when since_id is equal to max_id"                                   ! ExUserTimeline.e2^
    "be halcat0x15a statuses when screen_name equal to halcat0x15a"               ! ExUserTimeline.e3^
                                                                                  p^
  "statuses/retweeted_to_user should"                                             ^
    "contain a status when set 1 for count"                                       ! ExRetweetedToUser.e1^
    "be empty when since_id is equal to max_id"                                   ! ExRetweetedToUser.e2^
    "not contain halcat0x15a status when screen_name equal to halcat0x15a"        ! ExRetweetedToUser.e3^
                                                                                  p^
  "statuses/retweeted_by_user should"                                             ^
    "contain a status when set 1 for count"                                       ! ExRetweetedByUser.e1^
    "be empty when since_id is equal to max_id"                                   ! ExRetweetedByUser.e2^
    "be halcat0x15a statuses when screen_name equal to halcat0x15a"               ! ExRetweetedByUser.e3^
                                                                                  end

  type Auth = Required

  val auth = {
    val props = new java.util.Properties
    props.load(getClass.getResourceAsStream("/test.properties"))
    Required(props)
  }

  object number extends Given[Int] {
    def extract(text: String) = extract1(text).toInt
  }

  case class count[A <: Resource[List[Status], Required, A] with parameters.Paging](resource: A) extends Then[Int] {
    def extract(number: Int, text: String) = {
      val statuses = resource.count(number).unsafe
      val n = extract1(text).toInt
      statuses must have size n
    }
  }

  object id extends Given[Long] {
    def extract(text: String) = extract1(text).toLong
  }

  case class since[A <: Resource[List[Status], Required, A] with parameters.Paging](resource: A) extends Then[Long] {
    def extract(id: Long, text: String) = {
      val ids = resource.sinceId(id).unsafe.map(_.id)
      ids must not contain id
    }
  }

  object publictimeline {
    lazy val statuses = publicTimeline.unsafe
    lazy val e = statuses must have size 20
  }

  case object ExRetweetedByMe {
    lazy val statuses1 = retweetedByMe.count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = retweetedByMe.sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object ExRetweetedToMe {
    lazy val statuses1 = retweetedToMe.count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = retweetedToMe.sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object ExRetweetsOfMe {
    lazy val statuses1 = retweetsOfMe.count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = retweetsOfMe.sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
  }

  case object ExUserTimeline {
    lazy val statuses1 = userTimeline.screenName("halcat0x15a").count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = userTimeline.screenName("halcat0x15a").sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
    lazy val e3 = forall (statuses1) { status =>
      status.user.screenName must be equalTo "halcat0x15a"
    }
  }

  case object ExRetweetedToUser {
    lazy val statuses1 = retweetedToUser("halcat0x15a").count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = retweetedToUser("halcat0x15a").sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
    lazy val e3 = forall (statuses1) { status =>
      status.user.screenName must not equalTo "halcat0x15a"
    }
  }

  case object ExRetweetedByUser {
    lazy val statuses1 = retweetedByUser("halcat0x15a").count(1).unsafe
    lazy val e1 = statuses1 must have size 1
    lazy val id = statuses1.last.id
    lazy val statuses2 = retweetedByUser("halcat0x15a").sinceId(id).maxId(id).unsafe
    lazy val e2 = statuses2 must be empty
    lazy val e3 = forall (statuses1) { status =>
      status.user.screenName must be equalTo "halcat0x15a"
    }
  }

}
