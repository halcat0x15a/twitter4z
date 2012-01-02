package twitter4z.api

import scalaz._
import Scalaz._
import Validation.Monad._

import net.liftweb.json.scalaz.JsonScalaz.JSONR

import twitter4z.http.{ Method, HTTP, TwitterPromise }
import twitter4z.objects.JSON

trait API extends Timelines with Optionals { self: HTTP with JSON =>

  def filterParameters(parameters: Seq[(String, Optional[String])]): Seq[(String, String)] = parameters.collect {
    case (key, Value(value)) => key -> value
  }

  def resource[A: JSONR](method: Method, url: String, tokens: Option[Tokens], parameters: (String, Optional[String])*): TwitterPromise[A] = TwitterPromise(method(url).params(filterParameters(parameters): _*).oauth(tokens).processPromise(response[A]).map(_.join))

}
