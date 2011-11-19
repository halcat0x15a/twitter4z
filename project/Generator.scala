import sbt._
import scala.util.parsing.combinator._

trait Generator extends RegexParsers {

  lazy val toUpperCamel = (_: String).split("_").map(applyHead(_.toUpper)).mkString
    
  lazy val toLowerCamel = toUpperCamel andThen applyHead(_.toLower)

  type Result

  def parser: Parser[Result]

  def parseAll(f: File): Result = parseAll(parser, IO.read(f)) match {
    case Success(result, _) => result
    case Failure(msg, next) => sys.error("%s:%s:%s".format(f.name, msg, next.pos))
  }

  def generate(dir: File, resource: File): Seq[File]

  def applyHead(f: Char => Char)(s: String) = f(s.head) + s.tail

  def write(file: File, source: String) = {
    IO.write(file, source)
    file
  }

  val fileFilter = new FileFilter {
    def accept(pathname: File) = !pathname.name.endsWith("~")
  }

  def listFiles(resource: File) = IO.listFiles(resource, fileFilter)

  def eol = opt('\r') <~ '\n'

  // """[a-z_]+""".r
  val Key = """[a-z_A-Z]+""".r

  def name = """[a-zA-Z]+""".r

  val Typo = """[a-zA-Z\[\]]+""".r

  val colon = ':'

  val Url = """[a-z_/]+""".r

  val ValidUrl = """http.+""".r

  def resourceUrl = (ValidUrl | Url) ~ opt(colon ~> Key) ~ opt(Url)

  val APIParameter = """[a-z_\|\&]+""".r

  val comma = ','

  val Yes = """Yes""".r

  val Supported = """Supported""".r

  val No = """No""".r

  val Get = "GET".r

  val Post = "POST".r

  def method = Get | Post

  def auth = Yes | Supported | No

  val space = ' '

  def parameters = repsep(APIParameter, comma)

  val Or = """(.+?)\|(.+)""".r

  val And = """(.+?)\&(.+)""".r

  override def skipWhitespace: Boolean = false

}
