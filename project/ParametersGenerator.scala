import sbt._

object ParametersGenerator extends Generator {

  type Result = List[String ~ String ~ Option[String]]

  def parser = rep((Key <~ colon) ~ Typo ~ opt(colon ~> Typo) <~ eol)

  lazy val parseParameters = (resource: File) => parseAll(resource / "parameters")

  lazy val parameterMap = parseParameters.andThen(_ collect {
    case key ~ _ ~ Some(name) => key -> name
  } toMap)

  def generate(dir: File, resource: File): Seq[File] = {
    val parameters = parseParameters(resource) map {
      case key ~ typo ~ optName => {
	val name = optName.getOrElse(toUpperCamel(key))
	"""case class %s(_1: %s) extends AbstractParameter[%s]("%s")
implicit def Wrap%s(value: %s) = %s(value)""".format(name, typo, typo, key, name, typo, name)
      }
    } mkString("\n")
    val source = """package twitter4z.api

import scalaz._
import Scalaz._

sealed trait Parameter extends NewType[(String, String)]

sealed abstract class AbstractParameter[+A: Show](key: String) extends Parameter with Product1[A] {

  val value = key -> _1.shows

}

trait XParameters {
%s
}""".format(parameters)
    Seq(write(dir / "twitter4z" / "api" / "XParameters.scala", source))
  }

}
