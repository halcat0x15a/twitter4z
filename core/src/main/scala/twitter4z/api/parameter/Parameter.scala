package twitter4z.api.parameter

import scalaz._
import Scalaz._

import twitter4z.api._

trait Parameter[+A] {

  type Self <: Parameter[A] with A

  def apply(parameters: Parameters): Self

  val parameters: Parameters

  def apply[A: Show](key: String): A => Self = this(key) = _

  def update[A: Show](key: String, value: A): Self = apply(parameters + (key -> value.shows))

}
