import scalaz._
import Scalaz._

object ParameterGenerator extends ParameterGeneratorFunctions {

  lazy val parameterString: Parameter => ParameterString = {
    case (name, typo) => "%s: %s".format(toLowerCamel(name), typo)
  }

  lazy val source: Html => Option[SourceCode] = html => parameterStrings(html).map {
    case (required, optional) => (required |+| optional.map(_ |+| " = null")).mkString(", ")
  }

}
