package twitter4z.api

import scalaz._
import Scalaz._

import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._

import twitter4z.http._
import twitter4z.auth.OAuth
import twitter4z.objects.JSON

trait API extends Timelines with Tweets { self: OAuth with JSON =>

  abstract class AuthResource[A: JSONR](method: Evidence => Method, url: String) extends parameters.Parameters {

    def apply()(implicit ev: Evidence): Result[A] = method(ev)(url).params(parameters).process(parseJValue[A])

    def unary_!(implicit ev: Evidence) : A = apply() match {
      case Success(a) => a
      case Failure(e) => throw new Exception(e.toString)
    }

  }

}
