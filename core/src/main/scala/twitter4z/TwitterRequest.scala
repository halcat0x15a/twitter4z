package twitter4z

import java.net.HttpURLConnection

import scalaj.http.{ Http, HttpException }

import scalaz._
import Scalaz._
import scalaz.concurrent._

import twitter4z.http.Tokens

case class TwitterRequest(value: Http.Request) extends NewType[Http.Request] {

  def processValidation[A](processor: HttpURLConnection => A): TwitterResult[A] = try {
    value.process(processor).success
  } catch {
    case e: HttpException => TwitterHttpException(e).failNel
  }

  def processPromise[A] = (processValidation[A] _).promise

  def oauth(tokens: Tokens): TwitterRequest = TwitterRequest(value.oauth(tokens.consumer, tokens.token))

  def oauth(tokens: Option[Tokens]): TwitterRequest = TwitterRequest(tokens.foldl(value)(_.oauth(_)))

}
