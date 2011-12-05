package twitter4z.tetris

import java.awt.Color._
import swing._
import event._
import concurrent.stm._
import scalaz._
import Scalaz._

object Tetris extends SimpleSwingApplication {

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

  lazy val panel: Panel = new Panel {
    focusable = true
    preferredSize = new Dimension(Width, Height)
    override def paint(g: Graphics2D): Unit = {
      for {
	i <- 0 until Row
	j <- 0 until Column
      } {
	val x = Size * j
	val y = Size * i
	field.single()(i)(j) match {
	  case ImageBlock(image) => {
	    g.drawImage(image, x, y, x + Size, y + Size, top.peer)
	  }
	  case EmptyBlock => {
	    g.setColor(BLACK)
	    g.drawRect(x, y, x + Size, y + Size)
	  }
	}
      }
    }
    listenTo(keys)
    reactions += {
      case KeyPressed(_, key, _, _) => {
	fieldWorker ! key
      }
    }
  }

  lazy val repaintWorker: SwingWorker = new SwingWorker {
    def act(): Unit = {
      val fps = 1000 / 60
      loop {
	panel.repaint()
	Thread.sleep(fps)
      }
    }
  }

  def top = new MainFrame {
    resizable = false
    contents = panel
    Dialog.showInput(null, "PIN Code", "Auth", Dialog.Message.Plain, null, Seq.empty, "")
    fieldWorker.start()
    repaintWorker.start()
  }

}
