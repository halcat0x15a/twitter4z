package twitter4z.tetris

import scalaz._
import Scalaz._

case class Coord(x: Int, y: Int)

object Coord {

  implicit lazy val CoordEqual: Equal[Coord] = equalA

}
