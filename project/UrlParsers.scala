import scala.util.parsing.combinator.RegexParsers

trait UrlParsers extends RegexParsers { self: Generator =>

  lazy val pathString = """[\w\.]+""".r ^^ (_.replace(".format", ".json").replace("version", "1"))

  lazy val parameter = ':' ~> """\w+""".r ^^ toLowerCamel

}
