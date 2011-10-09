package twitter4z

import scalaj.http._

package object http {

  case class Timeout(connect: Int, read: Int)

  implicit def Request(request: Http.Request) = new {
    def option(timeout: Timeout) = request.options(HttpOptions.connTimeout(timeout.connect), HttpOptions.readTimeout(timeout.read))
    def oauth(tokens: Tokens): Http.Request = request.oauth(tokens.consumer, tokens.token)
    def oauth(tokens: Option[Tokens]): Http.Request = tokens.map(oauth).getOrElse(request)
  }

  type Method = String => Http.Request

  def get(url: String)(implicit timeout: Timeout) = Http(url).option(timeout)

  def post(url: String)(implicit timeout: Timeout) = Http.post(url).option(timeout)

}
