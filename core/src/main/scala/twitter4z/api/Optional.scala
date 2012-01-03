package twitter4z.api

import scalaz._
import Scalaz._

sealed abstract class Optional[+A: Parameter] {

  def show: Optional[String] = this match {
    case Value(a) => Value(implicitly[Parameter[A]].show(a))
    case Default => Default
  }

}

case class Value[+A: Parameter](value: A) extends Optional[A]

case object Default extends Optional[Nothing]

trait Optionals {

  implicit def toOptional[A: Parameter](a: A): Optional[A] = Value(a)

}
