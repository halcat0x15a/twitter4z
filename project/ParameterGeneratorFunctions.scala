import scala.util.parsing.combinator.RegexParsers

import scala.xml.NodeSeq

import org.fusesource.scalate.scuery.Selector

import scalaz._
import Scalaz._

trait ParameterGeneratorFunctions extends RegexParsers with Generator {

  type Name = String

  type Parameter = (Name, Type)

  type Parameters = Seq[Parameter]

  type ParameterString = String

  val parameterString: Parameter => ParameterString

  lazy val trimmedText: NodeSeq => String = _.text.trim

  type ParameterNodeSeq = NodeSeq

  def findExampleValues(parameter: ParameterNodeSeq): Option[ValuesString] = trimmedText(parameter \ "p" \ "tt") match {
    case "" => None
    case other => Some(other)
  }

  lazy val parameterName = """\w+""".r

  lazy val takeName: String => Option[String] = parseAll(parameterName, _) match {
    case Success(result, _) => result.some
    case _ => None
  }

  type ParameterType = String

  def findParam(parameter: ParameterNodeSeq): Option[(ParameterType, Name)] = {
    for {
      param <- (parameter \ "span").headOption
      name <- param.child.find(_.isAtom)
      taken <- takeName(trimmedText(name))
      typo <- (param \ "span").headOption
    } yield (trimmedText(typo), taken)
  }

  def parametersMap(html: Html): Option[Map[ParameterType, Parameters]] = Selector(".parameter").filter(html) match {
    case NodeSeq.Empty => Map("required" -> Nil, "optional" -> Nil).some
    case node => node.map { parameter =>
      for {
	param <- findParam(parameter)
	values <- findExampleValues(parameter)
      } yield param match {
	case (typo, name) => typo -> (name, ParameterTypeDeriver.derive(values))
      }
    }.sequence.map(_.groupBy(_._1).mapValues(_.map(_._2)))
  }

  type RequiredParameters = Parameters

  type OptionalParameters = Parameters

  lazy val parameters: Html => Option[(RequiredParameters, OptionalParameters)] = html => {
    parametersMap(html).map(map => (map.get _ >>> (_.orZero)).pair.fold(_ *** _)("required", "optional"))
  }

  lazy val mapParameterString: Parameters => Seq[ParameterString] = _.map(parameterString)

  lazy val parameterStrings = parameters >>> (_.map(mapParameterString.pair.fold(_ *** _)))

}
