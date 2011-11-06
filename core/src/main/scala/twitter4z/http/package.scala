package twitter4z

import scalaz._
import Scalaz._
import scalaj.http._

package object http {

  type Request = scalaj.http.Http.Request

  val ScalajHttp = scalaj.http.Http

  type Token = scalaj.http.Token

  def Token(key: String, secret: String) = scalaj.http.Token(key, secret)

  implicit def RichRequest(request: Request) = new {
    def oauth(tokens: Tokens): Request = request.oauth(tokens.consumer, tokens.token)
    def oauth(tokens: Option[Tokens]): Request = tokens.map(oauth) | request
  }

  type Method = String => Request

  def method(method: Method) = { url: String =>
    val timeout = {
      val timeout = for {
	value <- sys.props.get("twitter4z.timeout")
	timeout <- value.parseInt.toOption
      } yield timeout
      timeout | (1000 * 60)
    }
    method(url).options(HttpOptions.connTimeout(timeout), HttpOptions.readTimeout(timeout))
  }

  val get = method(ScalajHttp.apply)

  val post = method(ScalajHttp.post)

}
