package twitter4z.http

import scalaz._
import Scalaz._
import scalaz.concurrent._

import twitter4z.exception._

case class TwitterPromise[A](value: Promise[TwitterAPIResult[A]]) extends NewType[Promise[TwitterAPIResult[A]]]

object TwitterPromise {

  def apply[A](result: TwitterAPIResult[A]): TwitterPromise[A] = TwitterPromise(promise(result))

  implicit def TwitterPromiseFunctor = new Functor[TwitterPromise] {
    def fmap[A, B](m: TwitterPromise[A], f: A => B): TwitterPromise[B] = TwitterPromise {
      m.value.map {
	case Success(a) => a.copy(value=f(a.value).value).success
	case Failure(e) => e.fail
      }
    }
  }

  implicit def TwitterPromiseBind = new Bind[TwitterPromise] {
    def bind[A, B](m: TwitterPromise[A], f: A => TwitterPromise[B]): TwitterPromise[B] = TwitterPromise {
      m.value >>= {
	case Success(a) => f(a.value).value
	case Failure(e) => promise(e.fail[TwitterResponse[B]])
      }
    }
  }

  implicit def TwitterPromiseEach = new Each[TwitterPromise] {
    def each[A](m: TwitterPromise[A], f: A => Unit): Unit = m.value |>| (_ |>| (_.value |> f))
  }

}
