import org.fusesource.scalate.scuery.Selector

import scalaz._
import Scalaz._

object UrlGenerator extends UrlParsers with Generator {

  lazy val scheme = "http://".r | "https://".r

  lazy val urlPath = scheme ~ rep(pathString ~ opt('/')) ^^ {
    case scheme ~ url => scheme |+| url.map {
      case path ~ slash => path |+| ~slash.map(_.shows)
    }.mkString
  }

  lazy val urlPathTail = rep(opt('/') ~ pathString) ^^ {
    _.map {
      case slash ~ path => ~slash.map(_.shows) |+| path
    }.mkString
  }

  lazy val url = urlPath ~ opt(parameter ~ urlPathTail) ^^ {
    case url ~ None => """"%s"""".format(url)
    case url ~ Some(parameter ~ "") => """"%s" + %s""".format(url, parameter)
    case url ~ Some(parameter ~ tail) => """"%s" + %s + "%s"""".format(url, parameter, tail)
  }

  def source: Html => SourceCode = html => parseAll(url, (Selector(".field-doc-resource-url").filter(html) \ "div").text.trim) match {
    case Success(result, _) => result
  }

}
