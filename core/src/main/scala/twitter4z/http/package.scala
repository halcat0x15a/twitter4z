package twitter4z

import scalaj.http.Http.Request

import scalaz._
import Scalaz._

import net.liftweb.json.scalaz.JsonScalaz._

import twitter4z.exception.TwitterResult

package object http {

  type Token = scalaj.http.Token

  type Method = String => Request

  def Token(key: String, secret: String) = scalaj.http.Token(key, secret)

  implicit def ToTwitterRequest(value: Request) = TwitterRequest(value)

  type TwitterAPIResult[A] = TwitterResult[TwitterResponse[A]]

}
