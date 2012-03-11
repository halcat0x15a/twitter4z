package twitter4z.api

import scalaz._
import Scalaz._

import twitter4z.objects._

import parameter._

trait Users { self: API[_] =>

  case class LookupUsers(parameters: Parameters) extends Resource[List[User], Optional, LookupUsers](USERS / "lookup.json" <<?) with UserId {
    def apply(parameters: Parameters) = LookupUsers(parameters)
  }

  def lookupUsers[A: ContainsId](value: A) = LookupUsers(Map.empty).userId(value)
/*
  case class UserProfileImage(parameters: Parameters)(name: String) extends Resource[URL, Optional, UserProfileImage](USERS / "profile_image" / (name |+| ".json") <<?) {
    def apply(parameters: Parameters) = UserProfileImage(parameters)(name)
  }
*/
  case class SearchUsers(parameters: Parameters) extends Resource[List[User], Optional, SearchUsers](USERS / "search.json" <<?) with Query {
    def apply(parameters: Parameters) = SearchUsers(parameters)
    lazy val perPage = apply[Int](PAR_PAGE)
  }

  lazy val searchUsers = SearchUsers(Map.empty)

  case class ShowUser(parameters: Parameters) extends Resource[User, Optional, ShowUser](USERS / "show.json" <<?) with UserId {
    def apply(parameters: Parameters) = ShowUser(parameters)
  }

  def showUser[A: ContainsId](value: A) = ShowUser(Map.empty).userId(value)

}
