import sbt._

object Boilerplate extends Generator {

  val Url = """[a-z_/]+""".r

  val ValidUrl = """http.+""".r

  val ResourceUrl = (ValidUrl | Url) ~ opt(Colon ~> Key) ~ opt(Url)

  val APIParameter = """[a-z_\|\&]+""".r

  val Comma = ','

  val Yes = """Yes""".r

  val Supported = """Supported""".r

  val No = """No""".r

  val Auth = Yes | Supported | No

  val Space = ' '

  def parameterParser = repsep(APIParameter, Comma) <~ eol

  def parser = rep(((name <~ Colon) ~ typo <~ eol) ~ ((name <~ Space) ~ ResourceUrl <~ eol) ~ (Auth <~ eol) ~ parameterParser ~ parameterParser)

  val Or = """(.+?)\|(.+)""".r

  val And = """(.+?)\&(.+)""".r

  def generate(dir: File, resource: File): Seq[File] = {
    val parameters = ParametersGenerator.parameterMap(resource)
    listFiles(resource / "api") map { f =>
      val name = toUpperCamel(f.name)
      val functions = parseAll(parser, IO.read(f)) match {
	case Success(result, _) => result map {
	  case (name ~ typo) ~ (method ~ url) ~ auth ~ require ~ optional => {
	    lazy val createType: String => String = {
	      case Or(a, b) if !a.contains('&') => "Either[%s, %s]".format(createType(a), createType(b))
	      case And(a, b) if !a.contains('|') => "(%s, %s)".format(createType(a), createType(b))
	      case s => parameters.getOrElse(s, toUpperCamel(s))
	    }
	    val createParam = { key: String =>
	      val name = key match {
		case And(a, b) if !a.contains('|') => a + b
		case Or(a, b) if !a.contains('&') => a
		case _ => key
	      }
	      "%s: %s".format(toLowerCamel(name), createType(key))
	    }
	    val Arg = """(.+): .+""".r
	    val params = require.map(createParam) ::: optional.map(createParam.andThen(_ + " = null"))
	    val args = {
	      def notArg(arg: String) = url match {
		case _ ~ None ~ _ => true
		case _ ~ Some(urlArg) ~ _ => urlArg != arg
	      }
	      val args = params collect {
		case Arg(arg) if notArg(arg) => arg
	      } mkString(", ")
	      if (args.isEmpty) args else ", " + args
	    }
	    def parseTokens(f: String => String, n: String) = auth match {
	      case Yes() => f("Some")
	      case Supported() => f("Option")
	      case No() => n
	    }
	    val tokensPamram = parseTokens("(implicit tokens: %s[Tokens])".format(_: String), "")
	    val tokensArg = parseTokens(Function.const("tokens"), "None")
	    val urlString = url match {
	      case (url@ValidUrl()) ~ _ ~ _ => """"%s"""".format(url)
	      case a ~ Some(arg) ~ b => List(""""https://api.twitter.com/1/%s"""".format(a), toLowerCamel(arg), """"%s.json"""".format(b.getOrElse(""))).mkString(" + ")
	      case url ~ None ~ None => """"https://api.twitter.com/1/%s.json"""".format(url)
	    }
	    "  def %s(%s)%s = resource[%s](%s, %s, %s%s)".format(name, params.mkString(", "), tokensPamram, typo, method, urlString, tokensArg, args)
	  }
	} mkString("\n")
	case Failure(msg, next) => println(name, msg, next.pos);msg
      }
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
