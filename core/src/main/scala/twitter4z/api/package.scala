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

  def parseJson(input: InputStream): JValue = JsonParser.parse(new InputStreamReader(input))

  import Validation.Monad._

  type TwitterResult[A] = TwitterValidation[TwitterResponse[A]]

  def parse[A: JSONR](conn: HttpURLConnection): TwitterResult[A] = (TwitterValidation(fromJSON[A](Http.tryParse(conn.getInputStream, parseJson))) <**> TwitterValidation(RateLimit(conn)))(TwitterResponse.apply)

  def resource[A: JSONR](method: Method, url: String, tokens: OptionTokens, parameters: Seq[Parameter]*) = optOAuth(method(url).params(parameters.flatten.withFilter(null !=).map(_.value): _*))(tokens).processValidation(parse[A]).join
    

}
