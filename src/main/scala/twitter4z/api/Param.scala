package twitter4z.api

import scalaz._
import Scalaz._
import twitter4z.objects._

sealed abstract class Param[+A]

case class New[+A](value: A) extends Param[A]

case object Default extends Param[Nothing]

object Param {

  implicit object ParamMonad extends Monad[Param] {
    def bind[A, B](a: Param[A], f: (A) => Param[B]) = a match {
      case New(value) => f(value)
      case _ => Default
    }
    def pure[A](a: => A) = New(a)
  }

  implicit object ParamFoldable extends Foldable[Param] {
    override def foldRight[A, B](t: Param[A], b: => B, f: (A, => B) => B) = t match {
      case New(a) => f(a, b)
      case Default => b
    }
  }

  def param[A: Show](key: String) = ((value: Param[A]) => key -> value.map(_.shows))

  val TrimUser = param[Boolean]("trim_user")
  val IncludeEntities = param[Boolean]("include_entities")
  val Count = param[Int]("count")
  val SinceId = param[ID]("since_id")
  val MaxId = param[ID]("max_id")
  val Page = param[Int]("page")
  val IncludeRts = param[Boolean]("include_rts")
  val ContributorDetails = param[Boolean]("contributor_details")
  val ExcludeReplies = param[Boolean]("exclude_replies")
  val StringifyIds = param[Boolean]("stringify_ids")
  val Status = param[String]("status")
  val InReplyToStatusId = param[ID]("in_reply_to_status_id")
  val PlaceId = param[String]("place_id")
  val DisplayCoordinates = param[Boolean]("display_coordinates")
  val WrapLinks = param[Boolean]("wrap_links")

}
