import sbt._
import scala.util.parsing.combinator._

trait Generator extends RegexParsers {

  type Result

  def parser: Parser[Result]

  def parseAll(f: File): Result = parseAll(parser, IO.read(f)) match {
    case Success(result, _) => result
    case Failure(msg, next) => sys.error("%s:%s:%s".format(f.name, msg, next.pos))
  }

  def generate(dir: File, resource: File): Seq[File]

  def applyHead(f: Char => Char)(s: String) = f(s.head) + s.tail

  lazy val toUpperCamel = (_: String).split("_").map(applyHead(_.toUpper)).mkString
    
  lazy val toLowerCamel = toUpperCamel andThen applyHead(_.toLower)

  def write(file: File, source: String) = {
    IO.write(file, source)
    file
  }

  val fileFilter = new FileFilter {
    def accept(pathname: File) = !pathname.name.endsWith("~")
  }

  def listFiles(resource: File) = IO.listFiles(resource, fileFilter)

  def eol = opt('\r') <~ '\n'

  val Key = """[a-z_]+""".r

  def name = """[a-zA-Z]+""".r

  val Typo = """[a-zA-Z\[\]]+""".r

  val colon = ':'

  override def skipWhitespace: Boolean = false

}
