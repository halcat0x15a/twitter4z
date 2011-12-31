import scalaz._
import Scalaz._

object ArgumentGenerator extends ParameterGeneratorFunctions {

  lazy val parameterString: Parameter => ParameterString = {
    case (name, _) => """"%s" -> %s""".format(name, toLowerCamel(name))
  }

  lazy val sources = UrlGenerator.source &&& AuthGenerator.authArgument &&& parameterStrings

  def source(html: Html, method: MethodString): Option[SourceCode] = sources(html) match {
    case ((url, auth), Some((required, optional))) => (method +: url +: auth +: (required |+| optional)).mkString(", ").some
    case _ => None
  }

}
