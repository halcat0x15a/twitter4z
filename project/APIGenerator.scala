import sbt._

object APIGenerator extends Generator {

  val Url = """[a-z_/]+""".r

  val ValidUrl = """http.+""".r

  def resourceUrl = (ValidUrl | Url) ~ opt(colon ~> Key) ~ opt(Url)

  val APIParameter = """[a-z_\|\&]+""".r

  val comma = ','

  val Yes = """Yes""".r

  val Supported = """Supported""".r

  val No = """No""".r

  val Get = "GET".r

  val Post = "POST".r

  def method = Get | Post

  def auth = Yes | Supported | No

  val space = ' '

  type Result = List[(String ~ String) ~ (String ~ (String ~ Option[String] ~ Option[String])) ~ String ~ List[String] ~ List[String]]

  def parameterParser = repsep(APIParameter, comma) <~ eol

  def parser = rep(((name <~ colon) ~ Typo <~ eol) ~ ((method <~ space) ~ resourceUrl <~ eol) ~ (auth <~ eol) ~ parameterParser ~ parameterParser)

  val Or = """(.+?)\|(.+)""".r

  val And = """(.+?)\&(.+)""".r

  def generate(dir: File, resource: File): Seq[File] = {
    val parameters = ParametersGenerator.parameterMap(resource)
    def writeAPI(name: String, source:String) = write(dir / "twitter4z" / "api" / name, source)
    val apis: Seq[File] = listFiles(resource / "api").toSeq map { f =>
      val name = toUpperCamel(f.name)
      val functions = parseAll(f) map {
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
	  val tokensPamram = auth match {
	    case No() => ""
	    case s => {
	      val typo = s match {
		case Yes() => "Tokens"
		case Supported() => "OptionTokens"
	      }
	      "(implicit tokens: %s)".format(typo)
	    }
	  }
	  val tokensArg = auth match {
	    case No() => "NoneTokens"
	    case _ => "tokens"
	  }
	  val urlString = url match {
	    case (url@ValidUrl()) ~ _ ~ _ => """"%s"""".format(url)
	    case a ~ Some(arg) ~ b => List(""""https://api.twitter.com/1/%s"""".format(a), toLowerCamel(arg), """"%s.json"""".format(b.getOrElse(""))).mkString(" + ")
	    case url ~ None ~ None => """"https://api.twitter.com/1/%s.json"""".format(url)
	  }
	  "  def %s(%s)%s = resource[%s](%s, %s, %s%s)".format(name, params.mkString(", "), tokensPamram, typo, method.toLowerCase, urlString, tokensArg, args)
	}
      } mkString("\n")
      val source = """package twitter4z.api

import twitter4z.objects._
import twitter4z.http._
import twitter4z.json._
import twitter4z.auth._

trait %s { self: Http with Parameters =>
%s
}
""".format(name, functions)
      writeAPI(name + ".scala", source)
    }
    val source = """package twitter4z.api

import twitter4z.http._

trait APIs extends Parameters with %s { self: Http => }""".format(listFiles(resource / "api").map(f => toUpperCamel(f.name)).mkString(" with "))
    apis :+ writeAPI("APIs.scala", source)
  }

}
