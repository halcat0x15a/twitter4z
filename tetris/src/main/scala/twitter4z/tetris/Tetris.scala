package twitter4z.tetris

import java.awt.Color._
import javax.imageio.ImageIO
import swing._
import event._
import concurrent.stm._
import scalaz._
import Scalaz._

import twitter4z.Twitter._

object Tetris extends SimpleSwingApplication {
/*
  lazy val blocks: Ref[List[Block]] = Ref(EmptyField)

  lazy val tetrimino: Ref[Option[Tetrimino]] = Ref(None)

  lazy val blocksWorker: SwingWorker = new SwingWorker {
    def act(): Unit = {
      loop {
	receive {
	  case tetrimino: Tetrimino => atomic { implicit txn: InTxn =>
	    blocks.transform(_ ++ tetrimino.blocks)
	  }
	}
      }
    }
  }

  lazy val tetriminoWorker: SwingWorker = new SwingWorker {
    def act(): Unit = {
      def move(f: Tetrimino => Tetrimino) = atomic { implicit txn: InTxn =>
	tetrimino.transform(_.map(f))
	fieldWorker ! tetrimino.get
      }
      loop {
	receive {
	  case Key.Left => move(_.left)
	  case Key.Right => move(_.right)
	  case Key.Down => move(_.down)
	}
      }
    }
  }
*/
  lazy val panel = new Panel {
    focusable = true
    preferredSize = new Dimension(Width, Height)
    override def paint(g: Graphics2D): Unit = {
    }
    listenTo(keys)
    reactions += {
      case KeyPressed(_, key, _, _) => {
	key match {
	  case Key.Escape => quit()
	}
      }
    }
  }

  lazy val mainWorker: SwingWorker = new SwingWorker {
    def act(): Unit = {
      val fps = 1000 / 60
      @annotation.tailrec
      def loop(icons: List[Image]): Unit = {
	panel.repaint()
	Thread.sleep(fps)
	loop(icons)
      }
      for {
	statuses <- publicTimeline()
      } yield statuses.map(_.user.profile.imageURL).map(ImageIO.read(_))
    }
  }

  def top = new MainFrame {
    resizable = false
    contents = panel
    mainWorker.start()
  }

}
