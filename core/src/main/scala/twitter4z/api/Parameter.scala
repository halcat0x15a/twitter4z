package twitter4z.api

import scalaz._
import Scalaz._

import Parameters._

trait Parameter[A] {

  def param(value: A): Param

}

object Parameter {

  implicit object IdParameter extends Parameter[Id] {
    def param(value: Id) = value match {
      case UserId(id) => USER_ID -> id.shows
      case ScreenName(name) => SCREEN_NAME -> name
    }
  }

}

trait ParameterSyntax {

  def param[A](value: A)(implicit parameter: Parameter[A]) = parameter.param(value)

}
