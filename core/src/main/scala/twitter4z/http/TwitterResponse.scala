package twitter4z.http

import scalaz._
import Scalaz._
import twitter4z.api.RateLimit

case class TwitterResponse[A](value: A, rateLimit: RateLimit) extends NewType[A]
