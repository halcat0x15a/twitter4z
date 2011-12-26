package twitter4z

import scalaj.http.Http.Request

import scalaz._
import Scalaz._

import twitter4z.exception._

package object http {

  type Method = String => Request

  type Token = scalaj.http.Token

  def Token(key: String, secret: String) = scalaj.http.Token(key, secret)

  implicit def ToTwitterRequest(value: Request) = TwitterRequest(value)

  type TwitterAPIResult[A] = TwitterResult[TwitterResponse[A]]

}
