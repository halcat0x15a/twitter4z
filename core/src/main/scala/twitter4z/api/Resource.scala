package twitter4z.api

import scalaz._
import Scalaz._

import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._

import twitter4z.http._
import twitter4z.api.parameters.Parameters

abstract class Resource[A: JSONR](method: Method, url: String) extends Parameters {

  def apply(): Result[A] = method(url).params(parameters).process(parseJValue[A])

  def unary_! : A = apply() match {
    case Success(a) => a
    case Failure(e) => throw new Exception(e.toString)
  }

}
