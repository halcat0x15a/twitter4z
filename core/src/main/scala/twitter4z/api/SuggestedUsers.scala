package twitter4z.api

import scalaz._
import Scalaz._

import parameter._

trait SuggestedUsers { self: API[_] =>

  import twitter4z.objects._

  case class Suggestions(parameters: Parameters) extends Resource[List[Category], Optional, Suggestions](USERS / "suggestions.json" <<?) with Lang {
    def apply(parameters: Parameters) = suggestions(parameters)
  }

  def suggestions = Suggestions(Map.empty)

  case class UserSuggestions(parameters: Parameters)(slug: String) extends Resource[List[Category], Optional, UserSuggestions](USERS / "suggestions" / (slug |+| ".json") <<?) with Lang {
    def apply(parameters: Parameters) = UserSuggestions(parameters)(slug)
  }

  def suggestions(slug: String) = UserSuggestions(Map.empty)(slug)

  case class MemberSuggestions(parameters: Parameters)(slug: String) extends Resource[List[Category], Optional, MemberSuggestions](USERS / "suggestions" / slug / "members.json" <<?) with Lang {
    def apply(parameters: Parameters) = MemberSuggestions(parameters)(slug)
  }

  lazy val memberSuggestions = MemberSuggestions(Map.empty)_

}
