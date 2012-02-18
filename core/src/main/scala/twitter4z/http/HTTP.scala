package twitter4z.http

import scalaz._
import Scalaz._

import scalaj.http.{ Http, HttpOptions }

trait HTTP {

  val timeout: Timeout = Timeout(60 * 1000, 60 * 1000)

  private def timeouts(method: Method): Method = url => method(url).options(HttpOptions.connTimeout(timeout.conn), HttpOptions.readTimeout(timeout.read))

  def get: Method = timeouts(Http.apply)

  def post: Method = timeouts(Http.post)

}
