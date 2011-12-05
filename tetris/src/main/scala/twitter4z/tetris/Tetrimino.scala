package twitter4z.tetris

sealed trait Tetrimino {

  type Coord = (Int, Int)

  val coords: (Coord, Coord, Coord, Coord)

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
