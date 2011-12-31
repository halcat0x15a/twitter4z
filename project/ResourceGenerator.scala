import scala.util.parsing.combinator.RegexParsers

object ResourceGenerator extends RegexParsers with Generator {

  lazy val get = "GET".r ^^^ "get"

  lazy val post = "POST".r ^^^ "post"

  lazy val method = get | post

  lazy val resource = "[a-z_/:]+".r

  lazy val title = method ~ resource

  def source(html: Html): (Option[ArgumentGenerator.SourceCode], NameGenerator.SourceCode) = parse(title, (html \\ "h1").text) match {
    case Success(result, _) => result match {
      case method ~ resource => (ArgumentGenerator.source(html, method), NameGenerator.source(resource))
    }
  }

}
