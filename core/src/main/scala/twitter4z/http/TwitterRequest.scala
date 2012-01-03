package twitter4z.http

import java.net.HttpURLConnection

import scalaj.http.{ Http, HttpException }

import scalaz._
import Scalaz._
import scalaz.concurrent._

import twitter4z.exception._

case class TwitterRequest(value: Http.Request) extends NewType[Http.Request] {

  def processValidation[A](processor: HttpURLConnection => A): TwitterResult[A] = try {
    value.process(processor).success
  } catch {
    case e: HttpException => TwitterHttpException(e).failNel
  }

  def processPromise[A] = (processValidation[A] _).promise

  def oauth(tokens: OptionalTokens): TwitterRequest = TwitterRequest {
    tokens match {
      case DummyTokens => value
      case Tokens(consumer, token) => value.oauth(consumer, token)
    }
  }

}
