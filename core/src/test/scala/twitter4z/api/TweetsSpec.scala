package twitter4z.api

import org.specs2._

import twitter4z.Twitter._

class TweetsSpec extends Specification { def is =

  args(sequential=true, stopOnFail=true)                       ^
  "Tweets Resource Spec"                                       ^
                                                               p^
  "statuses/:id/retweeted_by should"                           ^
    "contain a user when set 1 for count"                      ! RetweetedBy.e1^
                                                               p^
  "statuses/:id/retweeted_by/ids should"                       ^
    "contain a id when set 1 for count"                        ! RetweetedByIds.e1^
                                                               p^
  "statuses/retweets/:id should"                               ^
    "contain a status when set 1 for count"                    ! Retweets.e1^
                                                               p^
  "statuses/show/:id should"                                   ^
    "be specified id status"                                   ! ShowStatus.e^
							       end

  implicit lazy val tokens = readTokens(getClass.getResourceAsStream("/test.tokens"))

  case object RetweetedBy {
    lazy val users1 = retweetedBy(105645059239723008L, Paging(count=1)).unsafe
    lazy val e1 = users1 must have size 1
  }

  case object RetweetedByIds {
    lazy val ids1 = retweetedBy(105645059239723008L, Paging(count=1)).unsafe
    lazy val e1 = ids1 must have size 1
  }

  case object Retweets {
    lazy val statuses1 = retweets(105645059239723008L, count=1).unsafe
    lazy val e1 = statuses1 must have size 1
  }

  case object ShowStatus {
    val id = 105645059239723008L
    lazy val status = showStatus(id).unsafe
    lazy val statusId = status.id
    lazy val e = statusId should be equalTo id
  }

}
