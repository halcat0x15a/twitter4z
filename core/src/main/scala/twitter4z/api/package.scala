package twitter4z

import java.io._
import scalaz._
import Scalaz._
import scalaj.http._
import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._
import http._
import json._

package object api {

  type StatusObject = twitter4z.objects.Status

  type User = twitter4z.objects.User

  type DirectMessage = twitter4z.objects.DirectMessage

  type HttpURLConnection = java.net.HttpURLConnection

  def parseJson(input: InputStream): JValue = JsonParser.parse(new InputStreamReader(input))

  def parse[A: JSONR](conn: HttpURLConnection): Result[A] = fromJSON[A](Http.tryParse(conn.getInputStream, parseJson))

  def resource[A: JSONR](method: Method, url: String, tokens: OptionTokens, optionalParameters: Seq[Parameter]*): TwitterResponse[A] = (TwitterResponse.apply[A] _).tupled(optOAuth(method(url).params(optionalParameters.flatten.withFilter(null !=).map(_.value): _*))(tokens).process(parse[A] _ &&& RateLimit.apply _))

}
