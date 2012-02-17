package twitter4z

import scalaz._
import Scalaz._
import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._

package object objects {

  type Indices = (Int, Int)

  type URL = java.net.URL

  type Count = Either[String, Int]

  type DateTime = org.joda.time.DateTime

  def JSONR[A](f: JValue => Result[A]): JSONR[A] = new JSONR[A] {
    def read(json: JValue) = f(json)
  }

  def jsonr[A](f: String => A): JSONR[A] = new JSONR[A] {
    def read(json: JValue) = json match {
      case JString(string) => Success(f(string))
      case x => UnexpectedJSONError(x, classOf[JString]).fail.toValidationNel
    }
  }

}
