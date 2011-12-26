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
import twitter4z.exception.{ TwitterException, TwitterResult, TwitterError, TwitterNumberFormatException }

package object api {

  type StatusObject = twitter4z.objects.Status

  type User = twitter4z.objects.User

  type DirectMessage = twitter4z.objects.DirectMessage

  def parseJson(input: InputStream): JValue = JsonParser.parse(new InputStreamReader(input))

  def parse[A: JsonScalaz.JSONR](conn: HttpURLConnection): JsonScalaz.Result[A] = JsonScalaz.fromJSON[A](Http.tryParse(conn.getInputStream, parseJson))//.fail.map2(TwitterError).validation

  def mapTwitterException[E, A](twitterException: E => TwitterException): ValidationNEL[E, A] => TwitterResult[A] = _.fail.map2(twitterException).validation

  def twitterResult[A: JsonScalaz.JSONR] = mapTwitterException[JsonScalaz.Error, A](TwitterError) *** mapTwitterException[NumberFormatException, RateLimit](TwitterNumberFormatException)

  def twitterResponse[A: JsonScalaz.JSONR] = (parse[A] _) &&& RateLimit.apply

  def response[A: JsonScalaz.JSONR](conn: HttpURLConnection): TwitterAPIResult[A] = twitterResult.apply(twitterResponse.apply(conn)).fold(_.<**>(_)(TwitterResponse[A]))

  def resource[A: JsonScalaz.JSONR](method: Method, url: String, tokens: Option[Tokens], parameters: Seq[Parameter]*): TwitterPromise[A] = TwitterPromise(method(url).params(parameters.flatten.withFilter(null !=).map(_.value): _*).oauth(tokens).processPromise(response[A]).map(_.join))

}
