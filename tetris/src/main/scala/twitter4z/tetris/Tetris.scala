package twitter4z.tetris

import java.awt.RenderingHints
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import swing._
import event._
import concurrent.stm._
import scalaz._
import Scalaz._

import twitter4z.Twitter._

object Tetris extends SimpleSwingApplication {

  lazy val block: Ref[Option[Block]] = Ref(None)

  lazy val blocks: TSet[Block] = TSet.empty

  def move(x: Int, y: Int): Unit = {
    block.single() |>| {
      case b@Block(image, Coord(xx, yy), _) => {
	atomic { implicit txn: InTxn =>
	  val xxx = (x |+| xx) |> (xxx => (xxx < 0).fold(0, xxx)) |> (xxx => ((xxx |+| 1) > Column).fold(Column - 1, xxx))
	  val yyy = (y |+| yy) |> (yyy => (yyy < 0).fold(0, yyy)) |> (yyy => ((yyy |+| 1) > Row).fold(Row - 1, yyy))
	  block() = b.copy(coord=Coord(xxx, yyy)).some
	}
      }
    }
  }

  lazy val panel = new Panel {
    focusable = true
    preferredSize = new Dimension(Width, Height)
    override def paint(g: Graphics2D): Unit = {
      g.setFont(DefaultFont)
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
      g.clearRect(0, 0, Width, Height)
      block.single() match {
	case Some(Block(image, Coord(x, y), text)) => {
	  val len = Width / FontSize
	  text.sliding(len, len).zipWithIndex |>| {
	    case (text, i) => g.drawString(text, i, (i |+| 1) * FontSize)
	  }
	  g.drawImage(image, null, x * Size, y * Size)
	}
	case None => {
	  g.drawString("Loading...", 0, FontSize)
	}
      }
      blocks.single.withFilter(null !=).foreach {
	case Block(image, Coord(x, y), text) => {
	  g.drawImage(image, null, x * Size, y * Size)
	}
      }
    }
    listenTo(keys)
    reactions += {
      case KeyPressed(_, key, _, _) => {
	key match {
	  case Key.Escape => quit()
	  case Key.Right => move(1, 0)
	  case Key.Left => move(-1, 0)
	  case Key.Space => move(0, Row)
	  case _ =>
	}
	repaint()
      }
    }
  }

  def updateBlock(): Unit = {
    for (statuses <- publicTimeline()) {
      atomic { implicit txn: InTxn =>
	val status = statuses.head
	block() |>| { block =>
	  blocks.add(block)
	}
	block() = Block(ImageIO.read(status.user.profile.imageURL), Coord(5, 0), status.text).some
      }
    }
  }

  lazy val mainWorker: SwingWorker = new SwingWorker {
    def act(): Unit = {
      updateBlock()
      loop {
	move(0, 1)
	panel.repaint()
	block.single() |>| { block =>
	  val y = block.coord.y |+| 1
	  (y === Row || blocks.single.exists(_.coord === Coord(block.coord.x, y))) when {
	    updateBlock()
	  }
	}
	Thread.sleep(SleepTime)
      }
    }
  }

  def top = new MainFrame {
    resizable = false
    contents = panel
    mainWorker.start()
  }

}
