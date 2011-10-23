import sbt._

object Boilerplate {

  def generateParameter(dir: File, resource: File) = {
    val parameters = IO.readLines((resource / "parameters").asFile) map { s =>
      val (key, typo, name) = (s.split(",").toList: @unchecked) match {
	case key :: typo :: name :: Nil => (key, typo, name)
	case key :: typo :: Nil => (key, typo, key.split("_").map(s => s.head.toUpper + s.tail).mkString)
      }
      """  case class %s(_1: %s) extends AbstractParameter[%s]("%s")
  implicit def Wrap%s(value: %s) = %s(value)""".format(name, typo, typo, key, name, typo, name)
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
