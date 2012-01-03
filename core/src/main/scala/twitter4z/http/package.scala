package twitter4z

import scalaj.http.{ Http, HttpOptions }

import scalaz._
import Scalaz._

import twitter4z.exception._
import twitter4z.Twitter._

package object http {

  type TwitterAPIResult[A] = TwitterResult[TwitterResponse[A]]

  type Key = String

  type Secret = String

  type Method = String => Http.Request

  def method(method: Method)(implicit timeout: Int) = (url: String) => (HttpOptions.connTimeout _ &&& HttpOptions.readTimeout _).apply(timeout).fold(method(url).options(_, _))

  lazy val get = method(Http.apply)

  lazy val post = method(Http.post)

}
