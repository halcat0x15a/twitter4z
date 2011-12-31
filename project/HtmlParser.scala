import sbt._

import java.io.{ FileInputStream }

import java.net.URL

import net.liftweb.common.Box

import net.liftweb.util.Html5

import scalaz._
import Scalaz._

trait HtmlParser { self: Generator =>

  type SourceDirectory = File

  type ManagedDirectory = File

  def parse(url: String): Box[Html] = {
    "downloading and parsing %s".format(url).println
    Html5.parse(new URL(url).openStream)
  }

  def parse(file: File): Box[Html] = {
    "parsing %s".format(file).println
    Html5.parse(new FileInputStream(file))
  }

  def files(dir: ManagedDirectory): Set[File] = IO.listFiles(dir).toSet

}
