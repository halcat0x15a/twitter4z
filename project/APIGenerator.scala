import sbt._

import net.liftweb.common.Full

import org.fusesource.scalate.scuery.Selector

import scalaz._
import Scalaz._

object APIGenerator extends Generator with HtmlParser {

  def source(functions: Seq[SourceCode]): SourceCode = """package twitter4z.api

import twitter4z.http.{ Http, Tokens }

trait API { self: Http =>

  type Boolean = java.lang.Boolean

  type Int = java.lang.Integer

  type Long = java.lang.Long

  type Double = java.lang.Double

%s

}
""".format(functions.mkString("\n"))

  def generate(src: SourceDirectory, dir: ManagedDirectory): Seq[File] = {
    APIWriter.write(dir)
    val functions = files(dir).map { file =>
      for {
	html <- parse(file)
	filter = (s: String) => Selector(s).filter(html)
	if !(filter(".breadcrumb") \ "a").exists(_.text.contains("Deprecated"))
      } yield {
	ObjectGenerator.source(html)
	ResourceGenerator.source(html) match {
	  case (argumentOption, name) => for {
	    parameter <- ParameterGenerator.source(html)
	    argument <- argumentOption
	    authParameter = AuthGenerator.authParameter(html)
	  } yield name -> """  def %s(%s)%s = resource(%s)""".format(name, parameter, authParameter, argument)
	}
      }
    }.collect {
      case Full(Some(value)) => value
    }.groupBy(_._1).values.map(_.toList).collect {
      case (_, source) :: Nil => source
    }.toSeq
    val file = src / "twitter4z" / "api" / "API.scala"
    IO.write(file, source(functions))
    Seq(file)
  }

}
