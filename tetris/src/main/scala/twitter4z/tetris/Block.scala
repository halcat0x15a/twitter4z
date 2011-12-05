package twitter4z.tetris

import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

case class Block(image: BufferedImage, coord: Coord)

object Block {

  def apply(url: URL): ImageBlock = ImageBlock(ImageIO.read(url))

}
