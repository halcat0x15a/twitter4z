package twitter4z.api

import scalaz._
import Scalaz._
import net.liftweb.json.scalaz.JsonScalaz._

case class TwitterResponse[A](value: Result[A], rateLimit: RateLimit.Result) extends NewType[Result[A]]
