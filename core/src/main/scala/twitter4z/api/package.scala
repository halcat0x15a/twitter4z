package twitter4z

import java.io.{ InputStream, InputStreamReader }
import java.net.HttpURLConnection
import scalaz._
import Scalaz._
import Validation.Monad._
import scalaj.http.Http
import net.liftweb.json.{ JValue, JsonParser }
import net.liftweb.json.scalaz.JsonScalaz
import http._
import json._

package object api {

  type StatusObject = twitter4z.objects.Status

  type User = twitter4z.objects.User

  type DirectMessage = twitter4z.objects.DirectMessage

  def parseJson(input: InputStream): JValue = JsonParser.parse(new InputStreamReader(input))

  type TwitterAPIResult[A] = TwitterResult[TwitterResponse[A]]

  def parse[A: JsonScalaz.JSONR](conn: HttpURLConnection): JsonScalaz.Result[A] = JsonScalaz.fromJSON[A](Http.tryParse(conn.getInputStream, parseJson))//.fail.map2(TwitterError).validation

  def mapTwitterException[E, A](e: E => TwitterException): ValidationNEL[E, A] => TwitterResult[A] = _.fail.map2(e).validation

  def twitterResult[A: JsonScalaz.JSONR] = mapTwitterException[JsonScalaz.Error, A](TwitterError) *** mapTwitterException[NumberFormatException, RateLimit](TwitterNumberFormatException)

  def twitterResponse[A: JsonScalaz.JSONR] = (parse[A] _) &&& RateLimit.apply

  def response[A: JsonScalaz.JSONR](conn: HttpURLConnection): TwitterAPIResult[A] = twitterResult.apply(twitterResponse.apply(conn)).fold(_.<**>(_)(TwitterResponse[A]))

  def resource[A: JsonScalaz.JSONR](method: Method, url: String, tokens: Option[Tokens], parameters: Seq[Parameter]*) = method(url).params(parameters.flatten.withFilter(null !=).map(_.value): _*).oauth(tokens).processValidation(response[A]).join

}
