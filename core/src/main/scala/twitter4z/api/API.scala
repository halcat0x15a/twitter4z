package twitter4z.api

import scalaz._
import Scalaz._
import Validation.Monad._

import net.liftweb.json.scalaz.JsonScalaz.JSONR

import twitter4z.http.{ Method, HTTP, TwitterPromise }
import twitter4z.json.JSON

trait API extends Timelines with Optionals { self: HTTP with JSON =>

  def resource[A: JSONR](method: Method, url: String, tokens: Option[Tokens], parameters: (String, Optional[String])*): TwitterPromise[A] = TwitterPromise(method(url).params(parameters.withFilter(_._2 /== Default).map(_.mapElements(identity, _.toString)): _*).oauth(tokens).processPromise(response[A]).map(_.join))

}
