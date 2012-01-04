package twitter4z.api

import scalaz._
import Scalaz._

sealed trait ID extends NewType[(String, String)] {

  val value: (String, String)

}

object ID {

  def apply(id: Long): ID = UserId(id)

  def apply(name: String): ID = ScreenName(name)

}

case class UserId(id: Long) extends ID {

  val value = "user_id" -> id.shows

}

case class ScreenName(name: String) extends ID {

  val value = "screen_name" -> name

}
