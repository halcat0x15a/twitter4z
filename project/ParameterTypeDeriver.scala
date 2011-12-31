import scala.util.parsing.combinator.JavaTokenParsers

object ParameterTypeDeriver extends JavaTokenParsers with Generator {

  lazy val boolean = ("true".r | "false".r) ^^^ "Boolean"

  lazy val int = wholeNumber ^^^ "Int"

  lazy val double = floatingPointNumber ^^^ "Double"

  lazy val value = boolean | int | double

  lazy val derive: ValuesString => Type = parseAll(value, _) match {
    case Success(result, _) => result
    case _: Failure => "String"
  }

}
