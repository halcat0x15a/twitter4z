package twitter4z.api

import scalaz._
import Scalaz._

trait Parameter[A] {

  def show(a: A): String

}

object Parameter {

  def parameter[A](f: A => String) = new Parameter[A] {
    def show(a: A): String = f(a)
  }

  def parameterA[A: Show]: Parameter[A] = parameter(_.shows)

  implicit lazy val NothingParameter = parameter[Nothing](_ => "")

  implicit lazy val StringParameter: Parameter[String] = parameterA

  implicit lazy val IntParameter: Parameter[Int] = parameterA

  implicit lazy val LongParameter: Parameter[Long] = parameterA

}
