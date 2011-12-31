import scalaz._
import Scalaz._

trait Generator {

  type ValuesString = String

  type Type = String

  type UnderscoreStyleString = String

  type UpperCamelString = String

  lazy val toUpperCamel: UnderscoreStyleString => UpperCamelString = _.split("_").map(_.show).map {
    case Nil => ""
    case head :: Nil => head.toUpper.shows
    case head :: tail => (head.toUpper +>: tail).mkString
  }.mkString

  type LowerCamelString = String

  lazy val toLowerCamel: UnderscoreStyleString => LowerCamelString = toUpperCamel(_).show match {
    case Nil => ""
    case head :: Nil => head.shows
    case head :: tail => (head.toLower +>: tail).mkString
  }

  type Html = scala.xml.Elem

  type SourceCode = String

  type Resource = String

  type MethodString = String

}
