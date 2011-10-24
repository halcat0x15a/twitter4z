package twitter4z.api

import scalaz._
import Scalaz._

case class ResourceURL(url: String) extends NewType[String] {

  val value = url

}
