package twitter4z

import java.io.{ InputStream, InputStreamReader }

import java.net.HttpURLConnection

import scalaz._
import Scalaz._
import Validation.Monad._

import scalaj.http.Http

import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz.{ JSONR, Error, Result, fromJSON }

import twitter4z.http._
import twitter4z.exception._

package object api {

  implicit def applyShow[A: Show](optional: Optional[A]): Optional[String] = optional match {
    case Value(a) => Value(a.shows)
    case Default => Default
  }

  def parseJson(input: InputStream): JValue = JsonParser.parse(new InputStreamReader(input))

  def parseJValue[A: JSONR]: HttpURLConnection => Result[A] = conn => fromJSON[A](Http.tryParse(conn.getInputStream, parseJson))

  def twitterResult[A: JSONR] = TwitterJsonError.lift[A] *** TwitterNumberFormatException.lift

  def twitterResponse[A: JSONR] = parseJValue[A] &&& RateLimit.validation

  def response[A: JSONR](conn: HttpURLConnection): TwitterAPIResult[A] = twitterResult.apply(twitterResponse.apply(conn)).fold(_ <*> _.map((TwitterResponse[A] _).flip.curried))

  def resource[A: JSONR](method: Method, url: String, tokens: OptionalTokens, parameters: Option[(String, String)]*): TwitterPromise[A] = TwitterPromise(TwitterRequest(method(url)).params(parameters).oauth(tokens).processPromise(response[A]).map(_.join))

}
