package twitter4z

import java.io.{ InputStream, InputStreamReader }

import java.net.HttpURLConnection

import scalaz._
import Scalaz._

import scalaj.http.Http

import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._

import twitter4z.http._
import twitter4z.exception._

package object api {

  def parseJson(input: InputStream): JValue = JsonParser.parse(new InputStreamReader(input))

  def parseJValue[A: JSONR](conn: HttpURLConnection): Result[A] = fromJSON[A](Http.tryParse(conn.getInputStream, parseJson))

}
