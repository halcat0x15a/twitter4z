package twitter4z.api

import scalaz._
import Scalaz._

import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._

import twitter4z.objects.JSON
import twitter4z.api.parameters.Parameter

import dispatch._
import dispatch.oauth.OAuth._
import dispatch.liftjson.Js._

import org.specs2.html._

trait API extends JSON with Timelines with Tweets {

  type Auth <: Authentication

  val auth: Auth

  sealed trait Execute[O <: Authentication] {

    def apply[A: JSONR](resource: Request): Result[(RateLimit, A)]

  }

  object Execute {

    def handler[A: JSONR](resource: Request) = resource >:+ {
      case (header, request) => (request ># fromJSON[A]) ~> (result => (RateLimit.validate(header) <**> result)(_ -> _))
    }

    def apply[A: JSONR](resource: Request, auth: Required): Result[(RateLimit, A)] = {
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

  abstract class Resource[A: JSONR, O <: Authentication, S <: Resource[A, O, S]](resource: Parameters => Request) extends Parameter {

    type Self = S

    def apply()(implicit execute: Execute[O]): Result[(RateLimit, A)] = execute(resource(parameters))

    def unsafe(implicit execute: Execute[O]): A = this() match {
      case Success((_, a)) => a
      case Failure(e) => throw new Exception(e.toString)
    }

  }

}
