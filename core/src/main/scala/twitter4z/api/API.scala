package twitter4z.api

import scalaz._
import Scalaz._

import twitter4z.http._
import twitter4z.objects.JSON

trait API extends Timelines with Optionals { self: HTTP with JSON => }
