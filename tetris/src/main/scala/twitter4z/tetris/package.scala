package twitter4z

import java.awt.Font

import scalaz._
import Scalaz._

package object tetris {

  val Row = 15

  val Column = 10

  val Size = 48

  val Width = Column * Size

  val Height = Row * Size

  val SleepTime = 1000

  val FontSize = Size / 2

  val DefaultFont: Font = new Font(Font.SANS_SERIF, Font.PLAIN, FontSize)

}

