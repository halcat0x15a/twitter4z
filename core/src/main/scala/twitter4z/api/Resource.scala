package twitter4z.api

import scalaz._
import Scalaz._

import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._

import twitter4z.http._

abstract class Resource[A: JSONR](method: Method, url: String) {

  type Self <: Resource[A]

  private var parameters = mzero[List[(String, String)]]

  def apply(): Result[A] = method(url).params(parameters).process(parseJValue[A])

}
