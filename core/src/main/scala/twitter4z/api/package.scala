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

  type Optional[A] = Option[A] @@ Wrapable

  val Default: Option[Nothing] @@ Wrapable = Tag(None)

  type Param = (String, String)

  def Param[A: Show](key: String, value: A): Param = key -> value.shows

  type Params = List[Param]

  def Params[A: Show](key: String, value: Optional[A]): Params = (value: Option[A]) match {
    case Some(value) => List(key -> value.shows)
    case None => Nil
  }

  def parseJson(input: InputStream): JValue = JsonParser.parse(new InputStreamReader(input))

  def parseJValue[A: JSONR](conn: HttpURLConnection): Result[A] = fromJSON[A](Http.tryParse(conn.getInputStream, parseJson))

}
