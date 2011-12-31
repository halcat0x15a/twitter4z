package twitter4z.api

import org.specs2._

import twitter4z.Twitter._

class TimelinesSpec extends Specification { def is =

  "Timelines Resource Spec"                 ^
                                            p^
  "statuses/home_timeline should"           ^
    "be success"                            ! e1^
                                            end

  implicit lazy val tokens = readTokens(getClass.getResourceAsStream("/test.tokens"))

  lazy val e1 = homeTimeline().get.toOption must beSome

}
