package twitter4z

import scalaz._
import Scalaz._
import scalaj.http._
import net.liftweb.json.scalaz.JsonScalaz._
import http._
import objects._
import json._

package object api {

  type UserID = Either[String, ID]

  def UserID(userId: UserID) = userId.fold("screen_name" -> _.pure[Param], "user_id" -> _.toString.pure[Param])

  type Statuses = List[Status]

  case class Location(lat: Double, long: Double) {
    def list = List("lat" -> lat.shows.pure[Param], "long" -> long.shows.pure[Param])
  }

  def resource[A](method: Method, string: String, tokens: Option[Tokens], optionalParameters: (String, Param[String])*)(implicit timeout: Timeout, jsonr: JSONR[A]) = {
    val params = optionalParameters.collect {
      case (k, New(v)) => k -> v
    }
    val request = method("""http://api.twitter.com/1/""" + string + """.json""").params(params: _*)
    tokens.fold(request.oauth(_), request)(parse[A])
  }

}
