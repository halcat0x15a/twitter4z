import sbt._

object ObjectsGenerator extends Generator {

  def eol = '\n'

  def typo = """\w+""".r

  def name = """\w+""".r

  def colon = ':'

  def line = name ~ colon ~ typo <~ eol ^^ {
    case name ~ _ ~ typo => "  %s: %s".format(toLowerCamel(name), typo)
  }

  def lines = rep(line)

  def generateObjects(dir: File, resource: File): Seq[File] = {
    val files = listFiles(resource / "objects")
    val objects = files map { f =>
      val name = toUpperCamel(f.name)
      val source = """package twitter4z.objects

case class %s(
%s
)

object %s {
  implicit def %sJSONR = 
}
""".format(name, parseAll(lines, IO.read(f)).get.mkString(",\n"), name, name)
      write(dir / "twitter4z" / "objects" / (name + ".scala"), source)
    }
    objects
  }

}
