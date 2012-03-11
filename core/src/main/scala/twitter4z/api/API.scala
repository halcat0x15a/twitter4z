package twitter4z.api

import scalaz._
import Scalaz._

import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._

import twitter4z.objects.JSON
import twitter4z.api.parameter.Parameter

import dispatch._
import dispatch.oauth.OAuth._
import dispatch.liftjson.Js._

import org.specs2.html._

trait API extends JSON with Timelines with Tweets with Search with DirectMessages with FriendsFollowers with Users with SuggestedUsers with Favorites with Lists {

  type Auth <: Authentication

  val auth: Auth

  type TwitterResult[A] = WriterT[Result, LastOption[RateLimit], A]

  sealed trait Execute[O <: Authentication] {

    def apply[A: JSONR](resource: Request): TwitterResult[A]

  }

  object Execute {

    def handler[A: JSONR](resource: Request) = resource >:+ {
      case (header, request) => (request ># fromJSON[A]) ~> (result => writerT[Result, LastOption[RateLimit], A]((RateLimit.validate(header) <**> result)(_.some.lst -> _)))
    }

    def apply[A: JSONR](resource: Request, auth: Required): TwitterResult[A] = {
      Http(handler[A](resource <@ (auth.consumer, auth.token)))
    }

    implicit def required(implicit ev: Auth =:= Required) = new Execute[Required] {
      def apply[A: JSONR](resource: Request) = Execute[A](resource, ev(auth))
    }

    implicit object optional extends Execute[Optional] {
      def apply[A: JSONR](resource: Request) = auth match {
	case auth: Required => Execute[A](resource, auth)
	case _ => Http(handler[A](resource))
      }
    }

  }

  abstract class Resource[A: JSONR, O <: Authentication, S <: Resource[A, O, S]](resource: Map[String, String] => Request) extends Parameter[S] {

    type Self = S

    def apply()(implicit execute: Execute[O]): TwitterResult[A] = execute(resource(parameters))

    def unsafe(implicit execute: Execute[O]): A = this().value match {
      case Success((_, a)) => a
      case Failure(e) => throw new Exception(e.toString)
    }

  }

}
