import sbt._

object ObjectsGenerator extends Generator {

  def line = (key <~ colon) ~ typo <~ eol

  def lines = rep(line)

  def generate(dir: File, resource: File): Seq[File] = listFiles(resource / "objects") map { f =>
    val name = toUpperCamel(f.name)
    val pairs = parseAll(lines, IO.read(f)).getOrElse(throw new Exception("Objects"))
    val fields = pairs map {
      case name ~ typo => "  %s: %s".format(toLowerCamel(name), typo)
    } mkString(",\n")
    val method = pairs.size match {
      case n if n > 12 =>""
      case n if n > 8 => ""
      case _ => {
	val args = pairs map {
	  case name ~ _ => """field("%s")""".format(name)
	} mkString(", ")
	"""(%s.apply _).applyJSON(%s)""".format(name, args)
      }
    }
    val source = """package twitter4z.objects

import net.liftweb.json.scalaz.JsonScalaz._

case class %s(
%s
)

object %s {
  implicit def %sJSONR: JSONR[%s] = %s
}
""".format(name, fields, name, name, name, method)
    write(dir / "twitter4z" / "objects" / (name + ".scala"), source)
  }

}
