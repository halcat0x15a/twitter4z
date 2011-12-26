package twitter4z.http

import scalaz._
import Scalaz._
import scalaz.concurrent._

case class TwitterPromise[A](value: Promise[TwitterAPIResult[A]]) extends NewType[Promise[TwitterAPIResult[A]]]

object TwitterPromise {

  def apply[A](result: TwitterAPIResult[A]): TwitterPromise[A] = TwitterPromise(promise(result))

  implicit def TwitterPromiseBind = new Bind[TwitterPromise] {
    def bind[A, B](m: TwitterPromise[A], f: A => TwitterPromise[B]): TwitterPromise[B] = TwitterPromise {
      m.value.flatMap {
	case Success(a) => f(a.value).value
	case Failure(e) => promise(e.fail[TwitterResponse[B]])
      }
    }
  }

}
