package twitter4z.api.parameter

import scalaz._
import Scalaz._

sealed trait Units

case object KM extends Units {
  override def toString = "km"
}

case object MI extends Units {
  override def toString = "mi"
}

object Units extends UnitsInstances

trait UnitsInstances {

  implicit lazy val UnitsShow = new Show[Units] {
    def show(unit: Units) = unit.toString.toList
  }

}
