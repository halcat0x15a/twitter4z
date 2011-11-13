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

  def resource[A](method: Method, url: String, tokens: OptionTokens, optionalParameters: Seq[Parameter]*)(implicit jsonr: JSONR[A]): Result[A] = fromJSON[A](tokens.value.foldl(method(url).params(optionalParameters.flatten.withFilter(null !=).map(_.value): _*))(_.oauth(_)).apply(parse))

}
