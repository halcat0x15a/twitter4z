package twitter4z

import java.io.{ InputStream, InputStreamReader }

import java.net.HttpURLConnection

import scalaz._
import Scalaz._
import Validation.Monad._

import scalaj.http.Http

import net.liftweb.json.{ JValue, JsonParser }
import net.liftweb.json.scalaz.JsonScalaz

import twitter4z.http._
import twitter4z.json._
import twitter4z.exception._

package object api {

  def parseJson(input: InputStream): JValue = JsonParser.parse(new InputStreamReader(input))

  def parseJValue[A: JsonScalaz.JSONR]: HttpURLConnection => JsonScalaz.Result[A] = conn => JsonScalaz.fromJSON[A](Http.tryParse(conn.getInputStream, parseJson))

  def twitterException[E, A](twitterException: E => TwitterException): ValidationNEL[E, A] => TwitterResult[A] = _.fail.map2(twitterException).validation

  def twitterResult[A: JsonScalaz.JSONR] = twitterException[JsonScalaz.Error, A](TwitterJsonError) *** twitterException[NumberFormatException, RateLimit](TwitterNumberFormatException)

  def twitterResponse[A: JsonScalaz.JSONR] = parseJValue[A] &&& RateLimit.validation

  def curried[A] = (TwitterResponse[A] _).flip.curried

  def response[A: JsonScalaz.JSONR](conn: HttpURLConnection): TwitterAPIResult[A] = twitterResult.apply(twitterResponse.apply(conn)).fold(_ <*> _.map(curried))

  type Parameter = (String, Any)

  def resource[A: JsonScalaz.JSONR](method: Method, url: String, tokens: Option[Tokens], parameters: Parameter*): TwitterPromise[A] = TwitterPromise(method(url).params(parameters.withFilter(_._1 != null).map(_.mapElements(identity, _.toString)): _*).oauth(tokens).processPromise(response[A]).map(_.join))

}
