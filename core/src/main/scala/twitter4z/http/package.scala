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

  def method(method: Method) = (url: String) => (HttpOptions.connTimeout _ &&& HttpOptions.readTimeout _).apply((sys.props.get("twitter4z.timeout") >>= ((_: String).parseInt.toOption)) | (1000 * 60)).fold(method(url).options(_, _))

  val get = method(ScalajHttp.apply)

  val post = method(ScalajHttp.post)

}
