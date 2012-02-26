package twitter4z.api

import scalaz._
import Scalaz._

import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._

import twitter4z.objects.JSON
import twitter4z.api.parameters.Parameters

import dispatch._
import dispatch.oauth.OAuth._
import dispatch.liftjson.Js._

trait API extends Timelines with Tweets with AuthenticationFunction { self: JSON =>

  type Auth <: Authentication

  val auth: Auth

  sealed trait Request[O <: Authentication] {
    def apply[A: JSONR](method: String, resource: String, params: Map[String, String]): Result[A]
  }

  object Request {
    def apply[A: JSONR](auth: Required, method: String, resource: String, params: Map[String, String]): Result[A] = Http(url(resource).copy(method=method) <<? params <@ (auth.consumer, auth.token) ># fromJSON[A])
    implicit def required(implicit ev: Auth =:= Required) = new Request[Required] {
      def apply[A: JSONR](method: String, resource: String, params: Map[String, String]) = Request[A](ev(auth), method, resource, params)
    }
    implicit case object optional extends Request[Optional] {
      def apply[A: JSONR](method: String, resource: String, params: Map[String, String]) = auth match {
	case auth: Required => Request[A](auth, method, resource, params)
	case _ => Http(url(resource).copy(method=method) ># fromJSON[A])
      }
    }
  }

  abstract class Resource[A: JSONR, O <: Authentication](method: String, resource: String) extends parameters.Parameters {

    def apply()(implicit request: Request[O]): Result[A] = request(method, resource, parameters.toMap)

    def unsafe(implicit request: Request[O]): A = this() match {
      case Success(a) => a
      case Failure(e) => throw new Exception(e.toString)
    }

  }

}
