package twitter4z.api

import scalaz._
import Scalaz._

sealed abstract class Optional[+A: Parameter]

case class Value[+A: Parameter](value: A) extends Optional[A]

case object Default extends Optional[Nothing]

trait Optionals {

  implicit def toOptional[A: Parameter](a: A): Optional[A] = Value(a)

  implicit def toOptionalString[A: Parameter](optional: Optional[A]): Optional[String] = optional match {
    case Value(a) => Value(implicitly[Parameter[A]].show(a))
    case Default => Default
  }

  implicit def OptionalEqual[A]: Equal[Optional[A]] = equalA

}
