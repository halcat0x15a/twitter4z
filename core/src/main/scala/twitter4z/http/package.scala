package twitter4z

import scalaz._
import Scalaz._
import net.liftweb.json.scalaz.JsonScalaz._

package object http {

  type Request = scalaj.http.Http.Request

  type Token = scalaj.http.Token

  type Method = Kleisli[Promise, String, Request]

  def Token(key: String, secret: String) = scalaj.http.Token(key, secret)

  implicit def ToTwitterRequest(value: Request) = TwitterRequest(value)

}
