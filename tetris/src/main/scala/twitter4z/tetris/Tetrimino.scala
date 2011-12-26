package twitter4z.tetris

import scalaz._
import Scalaz._
/*
sealed trait Tetrimino {

  val image: Image

  val model: Coords

  lazy val width: Int = model.toIndexedSeq

  private def move(f: Coord => Coord): Tetrimino = apply(coords.mapElements(f, f, f, f))

  def left = move(((_: Int) - 1).first)

  def right = move(((_: Int) + 1).first)

  def down = move(((_: Int) + 1).second)

  def blocks: List[Block] = coords.toIndexedSeq.map(Blcok(image, _))

}

case object I extends Tetrimino {

  val coords = ((0, 0), (1, 0), (2, 0), (3, 0))

}

case object O extends Tetrimino {

  val coords = ((0, 0), (1, 0), (1, 1), (0, 1))

}

case object S extends Tetrimino {

  val coords = ((0, 0), (1, 0), (1, 1), (2, 1))

}

case object Z extends Tetrimino {

  val coords = ((0, 1), (1, 1), (1, 0), (2, 0))

}

case object J extends Tetrimino {

  val coords = ((0, 1), (0, 0), (1, 0), (2, 0))

}

case object L extends Tetrimino {

  val coords = ((0, 0), (1, 0), (2, 0), (2, 1))

}

case object T extends Tetrimino {

  val coords = ((0, 0), (1, 0), (1, 1), (2, 0))

}
*/
