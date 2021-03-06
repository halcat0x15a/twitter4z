package twitter4z.api

import twitter4z.objects._

import parameter._

trait FriendsFollowers { self: API[_] =>

  case class FollowerIds(parameters: Parameters) extends Resource[List[Long], Optional, FollowerIds](FOLLOWERS / "ids.json" <<?) with UserId with Cursor {
    def apply(parameters: Parameters) = FollowerIds(parameters)
  }

  def followerIds[A: ContainsId](value: A) = FollowerIds(Map.empty).userId(value)

  case class FriendIds(parameters: Parameters) extends Resource[List[Long], Optional, FriendIds](FRIENDS / "ids.json" <<?) with UserId with Cursor {
    def apply(parameters: Parameters) = FriendIds(parameters)
  }

  def friendIds[A: ContainsId](value: A) = FriendIds(Map.empty).userId(value)

  case class ExistsFriendship(parameters: Parameters) extends Resource[Boolean, Optional, ExistsFriendship](FRIENDSHIPS / "exists.json" <<?) {
    def apply(parameters: Parameters) = ExistsFriendship(parameters)
    lazy val userIdA = apply[Long](USER_ID_A)
    lazy val userIdB = apply[Long](USER_ID_B)
    lazy val screenNameA = apply[String](SCREEN_NAME_A)
    lazy val screenNameB = apply[String](SCREEN_NAME_B)
    def userIdAB[A: ContainsId, B: ContainsId](valueA: A, valueB: B) = {
      (valueA, valueB) match {
	case (idA: Long, idB: Long) => userIdA(idA).userIdB(idB)
	case (idA: Long, nameB: String) => userIdA(idA).screenNameB(nameB)
	case (nameA: String, idB: Long) => screenNameA(nameA).userIdB(idB)
	case (nameA: String, nameB: String) => screenNameA(nameA).screenNameB(nameB)
      }
    }
  }

  def existsFriendship[A: ContainsId, B: ContainsId](valueA: A, valueB: B) = ExistsFriendship(Map.empty).userIdAB(valueA, valueB)

  case class IncomingFriendship(parameters: Parameters) extends Resource[Ids, Required, IncomingFriendship](FRIENDSHIPS / "incoming.json" <<?) with Cursor {
    def apply(parameters: Parameters) = IncomingFriendship(parameters)
  }

  lazy val incomingFriendship = IncomingFriendship(Map.empty)

  case class OutgoingFriendship(parameters: Parameters) extends Resource[Ids, Required, OutgoingFriendship](FRIENDSHIPS / "outgoing.json" <<?) with Cursor {
    def apply(parameters: Parameters) = OutgoingFriendship(parameters)
  }

  lazy val outgoingFriendship = OutgoingFriendship(Map.empty)

  case class ShowFriendship(parameters: Parameters) extends Resource[Relationship, Optional, ShowFriendship](FRIENDSHIPS / "show.json" <<?) {
    def apply(parameters: Parameters) = ShowFriendship(parameters)
    lazy val sourceId = apply[Long](SOURCE_ID)
    lazy val sourceScreenName = apply[String](SOURCE_SCREEN_NAME)
    lazy val targetId = apply[Long](TARGET_ID)
    lazy val targetScreenName = apply[String](TARGET_SCREEN_NAME)
    def sourceTargetId[A: ContainsId, B: ContainsId](valueA: A, valueB: B) = {
      (valueA, valueB) match {
	case (sourceId: Long, targetId: Long) => this.sourceId(sourceId).targetId(targetId)
	case (sourceId: Long, targetName: String) => this.sourceId(sourceId).targetScreenName(targetName)
	case (sourceName: String, targetId: Long) => sourceScreenName(sourceName).targetId(targetId)
	case (sourceName: String, targetName: String) => sourceScreenName(sourceName).targetScreenName(targetName)
      }
    }
  }

  def showFriendship[A: ContainsId, B: ContainsId](valueA: A, valueB: B) = ShowFriendship(Map.empty).sourceTargetId(valueA, valueB)

  case class CreateFriendship(parameters: Parameters) extends Resource[User, Required, CreateFriendship](FRIENDSHIPS / "create.json" <<) with UserId {
    def apply(parameters: Parameters) = CreateFriendship(parameters)
    lazy val follow = apply[Boolean](FOLLOW)
  }

  def createFriendship[A: ContainsId](value: A) = CreateFriendship(Map.empty).userId(value)

  case class DestroyFriendship(parameters: Parameters) extends Resource[User, Required, DestroyFriendship](FRIENDSHIPS / "destroy.json" <<) with UserId {
    def apply(parameters: Parameters) = DestroyFriendship(parameters)
  }

  def destroyFriendship[A: ContainsId](value: A) = DestroyFriendship(Map.empty).userId(value)

  case class LookupFriendship(parameters: Parameters) extends Resource[List[Friendship], Required, LookupFriendship](FRIENDSHIPS / "lookup.json" <<?) with UserId {
    def apply(parameters: Parameters) = LookupFriendship(parameters)
  }

  def lookupFriendship[A: ContainsId](value: A) = LookupFriendship(Map.empty).userId(value)

  case class UpdateFriendship(parameters: Parameters) extends Resource[Relationship, Required, UpdateFriendship](FRIENDSHIPS / "update.json" <<) with UserId {
    def apply(parameters: Parameters) = UpdateFriendship(parameters)
    lazy val device = apply[Boolean](DEVICE)
    lazy val retweets = apply[Boolean](RETWEETS)
  }

  def updateFriendship[A: ContainsId](value: A) = UpdateFriendship(Map.empty).userId(value)

  case class NoRetweetIds(parameters: Parameters) extends Resource[List[Long], Required, NoRetweetIds](FRIENDSHIPS / "no_retweet_ids.json" <<?) {
    def apply(parameters: Parameters) = NoRetweetIds(parameters)
  }

  lazy val noRetweetIds = NoRetweetIds(Map.empty)

}
