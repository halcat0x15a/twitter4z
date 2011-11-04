import sbt._

object APIGenerator extends Generator {

  def url = """[a-z/:]""".r

  def yes = "Yes".r

  def supported = "Supported".r

  def no = "No".r

  def colon = ':'

  def comma = ','

  def space = ' '

  def get = "GET".r

  def post = "POST".r

  def and = '&'

  def or = '|'

  def definition = (name <~ colon) ~ typo <~ eol

  def resource = (typo <~ space) ~ url <~ eol

  def auth = yes | supported | no <~ eol

  def parameters = repsep(key <~ eol, ',')

  def parser = rep(definition ~ resource ~ auth ~ parameters ~ parameters)

  def generate(dir: File, resource: File): Seq[File] = {
    listFiles(resource / "api") map { f =>
      val name = toUpperCamel(f.name)
      val functions = parseAll(parser, IO.read(f)).get map {
	case (name ~ typo) ~ (meth ~ url) ~ auth ~ require ~ optional =>
      }
    }
    null
  }

}
