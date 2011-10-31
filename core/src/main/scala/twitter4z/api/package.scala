package twitter4z

import scalaz._
import Scalaz._
import scalaj.http._
import net.liftweb.json.scalaz.JsonScalaz._
import http._
import json._

package object api {

  type StatusObject = twitter4z.objects.Status

  type User = twitter4z.objects.User

  type DirectMessage = twitter4z.objects.DirectMessage

  def resource[A](method: Method, url: String, tokens: Option[Tokens], optionalParameters: Seq[Parameter]*)(implicit jsonr: JSONR[A]): Result[A] = {
    val params = optionalParameters.flatten.filter(null !=).map(_.value)
    val request = method(url).params(params: _*)
    fromJSON[A](tokens.fold(request.oauth(_), request)(parse))
  }

}
