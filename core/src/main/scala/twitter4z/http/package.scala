package twitter4z

import scalaz._
import Scalaz._
import scalaj.http._
import net.liftweb.json.scalaz.JsonScalaz._

package object http {

  type Request = scalaj.http.Http.Request

  val ScalajHttp = scalaj.http.Http

  type Token = scalaj.http.Token

  type HttpURLConnection = java.net.HttpURLConnection

  def Token(key: String, secret: String) = scalaj.http.Token(key, secret)

  def oauth(request: Request)(tokens: Tokens): Request = request.oauth(tokens.consumer, tokens.token)

  def optOAuth(request: Request)(tokens: Option[Tokens]): Request = tokens.foldl(request)(oauth(_)(_))

  case class TwitterException(value: Either[HttpException, Either[NonEmptyList[Error], NonEmptyList[NumberFormatException]]]) extends NewType[Either[HttpException, Either[NonEmptyList[Error], NonEmptyList[NumberFormatException]]]]

  object TwitterException {

    def Right(value: Either[NonEmptyList[Error], NonEmptyList[NumberFormatException]]): TwitterException = TwitterException(Right(value))

  }

  type TwitterValidation[A] = Validation[TwitterException, A]

  object TwitterValidation {

    sealed trait E[E] {
      def either(value: NonEmptyList[E]): Either[NonEmptyList[Error], NonEmptyList[NumberFormatException]]
    }

    implicit lazy val ErrorE = new E[Error] {
      def either(value: NonEmptyList[Error]) = value.left
    }

    implicit lazy val NumberFormatExceptionE = new E[NumberFormatException] {
      def either(value: NonEmptyList[NumberFormatException]) = value.right
    }

    def apply[A](e: TwitterException): TwitterValidation[A] = e.fail

    def apply[A](e: HttpException): TwitterValidation[A] = apply(TwitterException(e.left))

    def apply[A, B](nel: NonEmptyList[B])(implicit e: E[B]): TwitterValidation[A] = apply(TwitterException.Right(e.either(nel)))

    def apply[A, B: E](validation: Validation[NonEmptyList[B], A]): TwitterValidation[A] = validation.fold(apply[A, B], success)

  }

  case class TwitterRequest(value: Request) extends NewType[Request] {

    def processValidation[A](processor: HttpURLConnection => A): TwitterValidation[A] = try {
      value.process(processor).success
    } catch {
      case e: HttpException => TwitterValidation(e)
    }

  }

  implicit def ToTwitterRequest(value: Request) = TwitterRequest(value)

  type Method = String => Request

  def method(method: Method) = (url: String) => (HttpOptions.connTimeout _ &&& HttpOptions.readTimeout _).apply((sys.props.get("twitter4z.timeout") >>= ((_: String).parseInt.toOption)) | (1000 * 60)).fold(method(url).options(_, _))

  lazy val get = method(ScalajHttp.apply)

  lazy val post = method(ScalajHttp.post)

}
