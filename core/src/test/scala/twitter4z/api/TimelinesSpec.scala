package twitter4z.api

import org.specs2._

import twitter4z.Twitter._

class TimelinesSpec extends Specification { def is =

  args(sequential=true) ^
  "Timelines Resource Spec"                      ^
                                                 p^
  "statuses/home_timeline should"                ^
    "contain 50 statuses when set 50 for count"  ! e1^
                                                 end

  implicit lazy val tokens = readTokens(getClass.getResourceAsStream("/test.tokens"))

  lazy val e1 = homeTimeline(count=50).unsafe must have size 50

}
