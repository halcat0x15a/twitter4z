package twitter4z.api.parameter

import scalaz._
import Scalaz._

sealed trait ListMode

case object Public extends ListMode {
  override def toString = "public"
}

case object Private extends ListMode {
  override def toString = "private"
}

object ListMode extends ListModeInstances

trait ListModeInstances {

  implicit object ListModeShow extends Show[ListMode] {
    def show(m: ListMode) = m.toString.toList
  }

}
