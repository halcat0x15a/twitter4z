import sbt._

object Boilerplate {

  def applyHead(f: Char => Char)(s: String) = s.split("_").map(s => f(s.head) + s.tail).mkString

  val toUpperCamel = applyHead(_.toUpper)_
    
  val toLowerCamel = toUpperCamel.andThen(applyHead(_.toLower))

  def generateParameter(dir: File, resource: File): File = {
    val parameters = IO.readLines(resource / "parameters") map { s =>
      val (key, typo, name) = (s.split(",").toList: @unchecked) match {
case key :: typo :: name :: Nil => (key, typo, name)
case key :: typo :: Nil => (key, typo, key.split("_").map(toUpperCamel).mkString)
      }
      """ case class %s(_1: %s) extends AbstractParameter[%s]("%s")
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
    val file = dir / "twitter4z" / "api" / "XParameters.scala"
    IO.write(file, source)
    file
  }

  def generateAPI(dir: File, resource: File): Seq[File] = {
    val ParametersRegex = """(.+),.+,(.+)""".r
    val parameters = IO.readLines(resource / "parameters") collect {
      case ParametersRegex(name, typo) => name -> typo
    } toMap
    val filter = new FileFilter {
      val Regex = """.*~""".r
      def accept(pathname: File) = !(PartialFunction.cond(pathname.name) {
	case Regex() => true
      })
    }
    IO.listFiles(resource / "api", filter) map { f =>
      val name = toUpperCamel(f.name)
      val functions = IO.readLines(f).sliding(2, 2).map(_.map(_.split(",").toList)) map { lll =>
	(lll: @unchecked) match {
	  case (name :: typo :: require) :: (auth :: method :: url :: optional) :: Nil => {
	    val Or = """(.+?)\|(.+?)""".r
	    val And = """(.+?)\&(.+?)""".r
	    lazy val createType: String => String = {
	      case Or(a, b) if !a.contains('&') => "Either[%s, %s]".format(createType(a), createType(b))
	      case And(a, b) if !a.contains('|') => "(%s, %s)".format(createType(a), createType(b))
	      case s => parameters.getOrElse(s, toUpperCamel(s))
	    }
	    val typeToParam = { s: String =>
	      val (name, typo) = s match {
		case Or(a, b) => (a, s)
		case s => (s, s)
	      }
              "%s: %s".format(toLowerCamel(name), createType(typo))
	    }
	    val Arg = """(.+): .+""".r
	    val params = require.map(typeToParam) ::: optional.map(typeToParam.andThen(_ + " = null"))
	    val args = {
	      val args = params collect {
		case Arg(arg) if arg != "id" && arg != "screenName" => arg
	      } mkString(", ")
	      if (args.isEmpty) args else ", " + args
	    }
	    def parseTokens(o: String => String, n: String) = auth match {
	      case "" => n
	      case s => o(s)
	    }
	    val tokensPamram = parseTokens("(implicit tokens: %s[Tokens])".format(_: String), "")
	    val tokensArg = parseTokens(Function.const("tokens"), "None")
	    val Url = """(.*/?):(\w+)(/?.*)""".r
	    val ValidUrl = """http.+""".r
	    val urlString = url match {
	      case s@ValidUrl() => """"%s"""".format(s)
	      case Url(a, arg, b) => """"%s" + %s + "%s"""".format(a, toLowerCamel(arg), b)
	      case s => """"http://api.twitter.com/1/%s.json"""".format(s)
	    }
	    "  def %s(%s)%s = resource[%s](%s, %s, %s%s)".format(name, params.mkString(", "), tokensPamram, typo, method, urlString, tokensArg, args)
	  }
	}
      } mkString("\n")
      val source = """package twitter4z.api

import twitter4z.objects._
import twitter4z.http._
import twitter4z.json._
import twitter4z.auth._

trait %s { self: Parameters =>
""".format(name) + functions + """
}
"""
      val file = dir / "twitter4z" / "api" / (name + ".scala")
      IO.write(file, source)
      file
    }
  }

}
