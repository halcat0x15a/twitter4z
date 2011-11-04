import sbt._
import scala.util.parsing.combinator._

trait Generator extends RegexParsers {

  def generate(dir: File, resource: File): Seq[File]

  def applyHead(f: Char => Char)(s: String) = f(s.head) + s.tail

  val toUpperCamel = (_: String).split("_").map(applyHead(_.toUpper)).mkString
    
  val toLowerCamel = toUpperCamel.andThen(applyHead(_.toLower))

  def write(file: File, source: String) = {
    IO.write(file, source)
    file
  }

  val fileFilter = new FileFilter {
    val Regex = """.*~""".r
    def accept(pathname: File) = !(PartialFunction.cond(pathname.name) {
      case Regex() => true
    })
  }

  def listFiles(resource: File) = IO.listFiles(resource, fileFilter)

  def eol = '\n'

  def key = """[a-z_]+""".r

  def name = """[a-zA-Z]+""".r

  def typo = """[a-zA-Z]+""".r

}
