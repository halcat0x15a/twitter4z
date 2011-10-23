package twitter4z

import scalaz._
import Scalaz._
import scalaj.http._

package object http {

  implicit def RichRequest(request: Http.Request) = new {
    def oauth(tokens: Tokens): Http.Request = request.oauth(tokens.consumer, tokens.token)
    def oauth(tokens: Option[Tokens]): Http.Request = tokens.map(oauth).getOrElse(request)
  }

  type Method = String => Http.Request

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

  val get = method(Http.apply)

  val post = method(Http.post)

}
