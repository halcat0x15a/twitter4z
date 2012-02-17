package twitter4z.api

import scalaz._
import Scalaz._

sealed trait Wrapable

object Optional {

  def apply[A](value: A): Option[A] @@ Wrapable = Tag(Some(value))

}

trait OptionalInstances {

  implicit def toOptional[A: Show](value: A): Optional[A] = Optional(value)

}
