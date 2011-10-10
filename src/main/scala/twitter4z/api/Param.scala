package twitter4z.api

import scalaz._
import Scalaz._
import twitter4z.objects._

sealed abstract class Param[+A]

case class New[+A](value: A) extends Param[A]

case object Default extends Param[Nothing]

object Param {

  implicit def ParamMonad = new Monad[Param] {
    def bind[A, B](a: Param[A], f: (A) => Param[B]) = a match {
      case New(value) => f(value)
      case _ => Default
    }
    def pure[A](a: => A) = New(a)
  }

  implicit def ParamFoldable = new Foldable[Param] {
    override def foldRight[A, B](t: Param[A], b: => B, f: (A, => B) => B) = t match {
      case New(a) => f(a, b)
      case Default => b
    }
  }

}
