package twitter4z.http

import java.net.HttpURLConnection

import scalaj.http.HttpException

import scalaz._
import Scalaz._

import twitter4z.{ TwitterHttpException, TwitterResult }

case class TwitterRequest(value: Request) extends NewType[Request] {

  def processValidation[A](processor: HttpURLConnection => A): TwitterResult[A] = try {
    value.process(processor).success
  } catch {
    case e: HttpException => TwitterHttpException(e).failNel
  }

  def oauth(tokens: Tokens): TwitterRequest = value.oauth(tokens.consumer, tokens.token)

  def oauth(tokens: Option[Tokens]): TwitterRequest = tokens.foldl(value)(_.oauth(_))

}
