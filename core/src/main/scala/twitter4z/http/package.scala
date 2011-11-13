package twitter4z

import scalaz._
import Scalaz._
import scalaj.http._

package object http {

  type Request = scalaj.http.Http.Request

  val ScalajHttp = scalaj.http.Http

  type Token = scalaj.http.Token

  def Token(key: String, secret: String) = scalaj.http.Token(key, secret)

  def oauth(request: Request)(tokens: Tokens): Request = request.oauth(tokens.consumer, tokens.token)

  def optOAuth(request: Request)(tokens: Option[Tokens]): Request = tokens.foldl(request)(oauth(_)(_))

  type Method = String => Request

  def method(method: Method) = (url: String) => (HttpOptions.connTimeout _ &&& HttpOptions.readTimeout _).apply((sys.props.get("twitter4z.timeout") >>= ((_: String).parseInt.toOption)) | (1000 * 60)).fold(method(url).options(_, _))

  lazy val get = method(ScalajHttp.apply)

  lazy val post = method(ScalajHttp.post)

}
