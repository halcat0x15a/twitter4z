package twitter4z.api

import dispatch._

import twitter4z.objects.{ Count => _, _ }
import parameter._

trait Lists { self: API[_] =>

  case class AllLists(parameters: Parameters) extends Resource[List[UserList], Optional, AllLists](LISTS / "all.json" <<?) with UserId {
    def apply(parameters: Parameters) = AllLists(parameters)
  }

  def allLists[A: ContainsId](value: A) = AllLists(Map.empty).userId(value)

  case class ListStatuses(parameters: Parameters) extends Resource[List[Status], Optional, ListStatuses](LISTS / "statuses.json" <<?) with ListId with Paging {
    def apply(parameters: Parameters) = ListStatuses(parameters)
  }

  def listStatuses[A: ContainsListId](value: A) = ListStatuses(Map.empty).listId(value)

  case class DestroyMember(parameters: Parameters = Map.empty) extends Resource[UserList, Required, DestroyMember](LISTS / "members" / "destroy.json" <<) with UserId with ListId {
    def apply(parameters: Parameters) = DestroyMember(parameters)
  }

  def destroyMember[A: ContainsId, B: ContainsListId](id: A)(listId: B) = DestroyMember().userId(id).listId(listId)

  case class Memberships(parameters: Parameters = Map.empty) extends Resource[CursorList[UserList], Optional, Memberships](LISTS / "memberships.json" <<?) with UserId with Cursor {
    def apply(parameters: Parameters) = Memberships(parameters)
  }

  def memberships[A: ContainsId](id: A) = Memberships().userId(id)

  case class Subscribers(parameters: Parameters = Map.empty) extends Resource[CursorList[UserList], Optional, Subscribers](LISTS / "subscribers.json" <<?) with ListId with Cursor {
    def apply(parameters: Parameters) = Subscribers(parameters)
  }

  def subscribers[A: ContainsListId](listId: A) = Subscribers(Map.empty).listId(listId)

  case class CreateSubscribers(parameters: Parameters = Map.empty) extends Resource[UserList, Required, CreateSubscribers](LISTS / "subscribers" / "create.json" <<) with ListId {
    def apply(parameters: Parameters) = CreateSubscribers(parameters)
  }

  def createSubscribers[A: ContainsListId](listId: A) = CreateSubscribers(Map.empty).listId(listId)

  case class ShowSubscribers(parameters: Parameters = Map.empty) extends Resource[User, Required, ShowSubscribers](LISTS / "subscribers" / "show.json" <<?) with UserId with ListId {
    def apply(parameters: Parameters) = ShowSubscribers(parameters)
  }

  def showSubscribers[A: ContainsId, B: ContainsListId](userId: A)(listId: B) = ShowSubscribers().userId(userId).listId(listId)

  case class DestroySubscribers(parameters: Parameters = Map.empty) extends Resource[UserList, Required, DestroySubscribers](LISTS / "subscribers" / "destroy.json" <<) with ListId {
    def apply(parameters: Parameters) = DestroySubscribers(parameters)
  }

  def destroySubscribers[A: ContainsListId](listId: A) = DestroySubscribers(Map.empty).listId(listId)

  case class CreateAllMembers(parameters: Parameters = Map.empty) extends Resource[UserList, Required, CreateAllMembers](LISTS / "members" / "create_all.json" <<) with ListId {
    def apply(parameters: Parameters) = CreateAllMembers(parameters)
    def userIds(id: Long, ids: Long*) = this(USER_ID) = List(id, ids).mkString(",")
    def screenNames(name: String, names: String*) = this(SCREEN_NAME) = List(name, names).mkString(",")
    def userIds[A: ContainsId: Manifest](x: A, xs: A*): Self = (x, xs.toArray) match {
      case (id: Long, ids: Array[Long]) => userIds(id, ids: _*)
      case (name: String, names: Array[String]) => screenNames(name, names: _*)
    }
  }

  def createAllMembers[A: ContainsId: Manifest, B: ContainsListId](x: A, xs: A*)(listId: B) = CreateAllMembers(Map.empty).userIds(x, xs: _*).listId(listId)

  case class ShowMember(parameters: Parameters = Map.empty) extends Resource[UserList, Required, ShowMember](LISTS / "members" / "show.json" <<?) with UserId with ListId {
    def apply(parameters: Parameters) = ShowMember(parameters)
  }

  def showMember[A: ContainsId, B: ContainsListId](userId: A)(listId: B) = ShowMember(Map.empty).userId(userId).listId(listId)

  case class Members(parameters: Parameters = Map.empty) extends Resource[CursorList[User], Optional, Members](LISTS / "members.json" <<?) with ListId {
    def apply(parameters: Parameters) = Members(parameters)
  }

  def members[A: ContainsListId](listId: A) = Members(Map.empty).listId(listId)

  case class CreateMember(parameters: Parameters = Map.empty) extends Resource[UserList, Required, CreateMember](LISTS / "members" / "create.json" <<) with UserId with ListId {
    def apply(parameters: Parameters) = CreateMember(parameters)
  }

  def createMember[A: ContainsId, B: ContainsListId](userId: A)(listId: B) = CreateMember().userId(userId).listId(listId)

  case class DestroyList(parameters: Parameters = Map.empty) extends Resource[UserList, Required, DestroyList](LISTS / "destroy.json" <<) with ListId {
    def apply(parameters: Parameters) = DestroyList(parameters)
  }

  def destroyList[A: ContainsListId](listId: A) = DestroyList().listId(listId)

  case class UpdateList(parameters: Parameters = Map.empty) extends Resource[UserList, Required, UpdateList](LISTS / "update.json" <<) with ListId with ListParameter {
    def apply(parameters: Parameters) = UpdateList(parameters)
  }

  def updateList[A: ContainsListId](listId: A) = UpdateList().listId(listId)
  
  case class CreateList(parameters: Parameters = Map.empty) extends Resource[UserList, Required, CreateList](LISTS / "create.json" <<) with ListParameter {
    def apply(parameters: Parameters) = CreateList(parameters)
  }

  lazy val createList = CreateList().name
  
  case class Lists(parameters: Parameters = Map.empty) extends Resource[CursorList[UserList], Optional, Lists](API_TWITTER_COM / "lists.json" <<?) with UserId with Cursor {
    def apply(parameters: Parameters) = Lists(parameters)
  }

  def lists[A: ContainsId](id: A) = Lists().userId(id)
  
  case class ShowList(parameters: Parameters = Map.empty) extends Resource[UserList, Optional, ShowList](LISTS / "show.json" <<?) with ListId {
    def apply(parameters: Parameters) = ShowList(parameters)
  }

  def lists[A: ContainsListId](id: A) = ShowList().listId(id)

  case class Subscriptions(parameters: Parameters = Map.empty) extends Resource[CursorList[UserList], Optional, Subscriptions](LISTS / "subscriptions.json" <<?) with UserId with Count with Cursor {
    def apply(parameters: Parameters) = Subscriptions(parameters)
  }

  def subscriptions[A: ContainsId](id: A) = Subscriptions().userId(id)

}
