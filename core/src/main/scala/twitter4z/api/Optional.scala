package twitter4z.api

import scalaz._
import Scalaz._

sealed trait Optional[+A] {

  def apply(key: String): Option[(String, String)]

  val option: Option[A]

}

case class Value[A: Show](value: A) extends Optional[A] {

  def apply(key: String) = Some(key -> value.shows)

  val option = value.some

}

case object Default extends Optional[Nothing] {

  def apply(key: String) = None

  val option = none

}

trait OptionalInstances {

  implicit def toOptional[A: Show](value: A): Optional[A] = Value(value)

}
