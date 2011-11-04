import sbt._

object Boilerplate extends Generator{

//  def parser = rep(((name <~ comma) ~ typo ~ repsep(key, ',') <~ eol) ~ (typo <~ comma

  def generate(dir: File, resource: File): Seq[File] = {
    val parameters = ParametersGenerator.parameterMap(resource)
    listFiles(resource / "api") map { f =>
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
%s
}
""".format(name, functions)
      write(dir / "twitter4z" / "api" / (name + ".scala"), source)
    }
  }

}
