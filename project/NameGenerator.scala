import scalaz._
import Scalaz._

object NameGenerator extends UrlParsers with Generator {

  lazy val urlString: List[String] => String = url => toLowerCamel {
    url.mkString("_")
  }

  lazy val urlPath = rep(pathString <~ opt('/')) ^^ urlString

  lazy val urlPathTail = rep(opt('/') ~> pathString) ^^ urlString

  lazy val urlBody = urlPath ~ opt(parameter ~ urlPathTail) ^^ {
    case url ~ Some(_ ~ tail) => url |+| tail
    case url ~ None => url
  }

  def source(resource: Resource): SourceCode = parseAll(urlBody, resource) match {
    case Success(result, _) => result
  }

}
