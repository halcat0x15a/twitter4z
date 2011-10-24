package twitter4z

import scalaz._
import Scalaz._
import scalaj.http._
import net.liftweb.json.scalaz.JsonScalaz._
import http._
import objects._
import json._

package object api {

  type Statuses = List[Status]

  implicit def StringToResourceURL(string: String) = ResourceURL("http://api.twitter.com/1/" + string + ".json")

  def resource[A](method: Method, url: ResourceURL, tokens: Option[Tokens], optionalParameters: Parameter*)(implicit jsonr: JSONR[A]) = {
    val params = optionalParameters.filter(null !=).map(_.value)
    val request = method(url).params(params: _*)
    fromJSON[A](tokens.fold(request.oauth(_), request)(parse))
  }

}