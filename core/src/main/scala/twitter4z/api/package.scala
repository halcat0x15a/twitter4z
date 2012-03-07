package twitter4z

import org.specs2.html._
import dispatch._

package object api {

  type Parameters = Map[String, String]

  val Default: Parameters = Map.empty

  val TwitterAPI = :/("api.twitter.com")

  // UnionTypes

  type ![A] = A => Nothing
  type !![A] = ![![A]]

  trait Disj { self =>
    type D
    type t[S] = Disj {
      type D = self.D with ![S]
    }
  }

  type t[T] = {
    type t[S] = (Disj { type D = ![T] })#t[S]
  }

  type or[T <: Disj] = ![T#D]

  type Contains[S, T <: Disj] = !![S] <:< or[T]
  type ∈[S, T <: Disj] = Contains[S, T]

  sealed trait Union[T] {
    val value: Any
  }

  case class Converter[S](s: S) {
    def union[T <: Disj](implicit ev: Contains[S, T]): Union[T] =
      new Union[T] {
        val value = s
      }
  }

  implicit def any2Converter[S](s: S): Converter[S] = Converter[S](s)

}
