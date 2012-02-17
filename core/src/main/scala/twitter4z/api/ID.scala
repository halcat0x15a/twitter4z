package twitter4z.api

import scalaz._
import Scalaz._

sealed abstract class Id

case class UserId(id: Long) extends Id

case class ScreenName(name: String) extends Id

trait IdInstances {

  implicit object IdShow {
    def show(id: Id) = id match {
      case UserId(id) => id.show
      case ScreenName(name) => name.show
    }
  }

  implicit def wrapId(id: Long): Id = UserId(id)

  implicit def wrapId(name: String): Id = ScreenName(name)

}
