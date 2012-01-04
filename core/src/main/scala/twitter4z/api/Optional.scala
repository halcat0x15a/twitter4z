package twitter4z.api

import scalaz._
import Scalaz._

sealed trait Optional[+A] {

  val option: Option[A]

}

case class Value[+A](value: A) extends Optional[A] {

  val option = value.some

}

case object Default extends Optional[Nothing] {

  val option = none

}

trait Optionals {

  implicit def toOptional[A](a: A): Optional[A] = Value(a)

}
