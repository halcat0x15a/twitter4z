package twitter4z.api

import scalaz._
import Scalaz._
import net.liftweb.json.scalaz.JsonScalaz._

case class RateLimit(limit: Int, remaining: Int, reset: Long)

object RateLimit {

  type Header = Map[String, Seq[String]]

  def error(key: String, desc: String): Error = UncategorizedError(key, desc, Nil)

  def wrap[A](f: String => Validation[NumberFormatException, A])(s: String): Result[A] = (f(s).fail >| error("parse", "for input string: %s".format(s))).validation.liftFailNel

  def get[A](key: String)(f: String => Result[A]): Header => Result[A] = _.get(key).toSuccess(error("get", "key not found: %s".format(key))).liftFailNel.flatMap(_.headOption.toSuccess(error("head", "next on empty iterator")).liftFailNel).flatMap(f)

  lazy val limit = get("x-ratelimit-limit")(wrap(_.parseInt))

  lazy val remaining = get("x-ratelimit-remaining")(wrap(_.parseInt))

  lazy val reset = get("x-ratelimit-reset")(wrap(_.parseLong))

  lazy val validate = (reset &&& (remaining &&& limit)) >>> (_.fold(_ <*> _.fold(_ <*> _.map((RateLimit.apply _).curried))))

}
