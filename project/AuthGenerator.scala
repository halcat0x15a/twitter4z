import org.fusesource.scalate.scuery.Selector

import scalaz._
import Scalaz._

object AuthGenerator extends Generator {

  type AuthString = String

  lazy val authString: Html => AuthString = html => (Selector(".api-doc-block").filter(html) \\ "a").last.text.trim

  def matchAuthString(yes: SourceCode, supported: SourceCode, no: SourceCode): Html => SourceCode = authString >>> {
    case "Yes" => yes
    case "Supported" => supported
    case "No" => no
  }

  lazy val authParameter: Html => SourceCode = matchAuthString("(implicit tokens: Tokens)", "(implicit tokens: Option[Tokens])", "")

  lazy val authArgument: Html => SourceCode = matchAuthString("tokens", "tokens", "None")

}
