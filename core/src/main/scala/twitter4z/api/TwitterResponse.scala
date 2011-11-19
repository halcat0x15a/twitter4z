package twitter4z.api

import scalaz._
import Scalaz._
import net.liftweb.json.scalaz.JsonScalaz._

case class TwitterResponse[A](value: A, rateLimit: RateLimit) extends NewType[A]
