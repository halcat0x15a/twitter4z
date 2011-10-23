import sbt._

object Boilerplate {

  def generateParameter(dir: File, resource: File) = {
    val parameters = IO.readLines((resource / "parameters").asFile) map { s =>
      (s.split(":").toList: @unchecked) match {
	case name :: typo :: key :: Nil => "  case class " + name + "(_1: " + typo + ") extends AbstractParameter[" + typo + "](\"" + key + """")
  implicit def Wrap""" + name + "(value: " + typo + ") = " + name + "(value)"
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
""" + parameters + """
}"""
    val file = (dir / "twitter4z" / "api" / "XParameters.scala").asFile
    IO.write(file, source)
    file
  }

}
