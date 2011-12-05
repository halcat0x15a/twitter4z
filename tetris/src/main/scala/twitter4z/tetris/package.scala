package twitter4z\

import scalaz._
import Scalaz._

package object tetris {

  type Field = List[List[Block]]

  type Coord = (Int, Int)

  type Coords = (Coord, Coord, Coord, Coord)

  type Image = java.awt.image.BufferedImage

  val Row = 20

  val Column = 10

  val Size = 20

  val Width = Column * Size

  val Height = Row * Size

  val EmptyField: Field = List.fill(Row, Column)(EmptyBlock)

}
