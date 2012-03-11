package twitter4z.api

import dispatch._

import twitter4z.objects._
import parameter._

trait Lists { self: API =>

  case class AllLists(parameters: Parameters) extends Resource[List[UserList], Optional, AllLists](LISTS / "all.json" <<?) with UserId {
    def apply(parameters: Parameters) = AllLists(parameters)
  }

  def allLists[A: ContainsId](value: A) = AllLists(Map.empty).userId(value)

  case class ListStatuses(parameters: Parameters) extends Resource[List[Status], Optional, ListStatuses](LISTS / "statuses.json" <<?) with ListId with Paging {
    def apply(parameters: Parameters) = ListStatuses(parameters)
  }

  def listStatuses(listId: Long) = ListStatuses(Map.empty).listId(listId)

  def listStatuses(slug: String, ownerId: Long) = ListStatuses(Map.empty).slug(slug).ownerId(ownerId)

  def listStatuses(slug: String, ownerName: String) = ListStatuses(Map.empty).slug(slug).ownerScreenName(ownerName)

  case class DestroyMember(parameters: Parameters = Map.empty) extends Resource[UserList, Required, DestroyMember](LISTS / "members" / "destroy.json" <<) with UserId with ListId {
    def apply(parameters: Parameters) = DestroyMember(parameters)
  }

  def destroyMember(userId: Long, listId: Long) = DestroyMember().userId(userId).listId(listId)

  def destroyMember(userId: Long, slug: String, ownerId: Long) = DestroyMember().userId(userId).slug(slug).ownerId(ownerId)

  def destroyMember(userId: Long, slug: String, ownerName: String) = DestroyMember().userId(userId).slug(slug).ownerScreenName(ownerName)

  def destroyMember(screenName: String, listId: Long) = DestroyMember().screenName(screenName).listId(listId)

  def destroyMember(screenName: String, slug: String, ownerId: Long) = DestroyMember().screenName(screenName).slug(slug).ownerId(ownerId)

  def destroyMember(screenName: String, slug: String, ownerName: String) = DestroyMember().screenName(screenName).slug(slug).ownerScreenName(ownerName)

  case class Memberships(parameters: Parameters = Map.empty) extends Resource[UserLists, Optional, Memberships](LISTS / "memberships.json" <<?) with UserId with Cursor {
    def apply(parameters: Parameters) = Memberships(parameters)
  }

  def memberships(userId: Long) = Memberships().userId(userId)

  def memberships(screenName: String) = Memberships().screenName(screenName)

  case class Subscribers(parameters: Parameters = Map.empty) extends Resource[UserLists, Optional, Subscribers](LISTS / "subscribers.json" <<?) with ListId with Cursor {
    def apply(parameters: Parameters) = Subscribers(parameters)
  }

  def subscribers(listId: Long) = Subscribers(Map.empty).listId(listId)

  def subscribers(slug: String, ownerId: Long) = Subscribers(Map.empty).slug(slug).ownerId(ownerId)

  def subscribers(slug: String, ownerName: String) = Subscribers(Map.empty).slug(slug).ownerScreenName(ownerName)

  case class CreateSubscribers(parameters: Parameters = Map.empty) extends Resource[UserList, Required, CreateSubscribers](LISTS / "subscribers" / "create.json" <<) with ListId {
    def apply(parameters: Parameters) = CreateSubscribers(parameters)
  }

  def createSubscribers(listId: Long) = CreateSubscribers(Map.empty).listId(listId)

  def createSubscribers(slug: String, ownerId: Long) = CreateSubscribers(Map.empty).slug(slug).ownerId(ownerId)

  def createSubscribers(slug: String, ownerName: String) = CreateSubscribers(Map.empty).slug(slug).ownerScreenName(ownerName)

  case class ShowSubscribers(parameters: Parameters = Map.empty) extends Resource[User, Required, ShowSubscribers](LISTS / "subscribers" / "show.json" <<?) with UserId with ListId {
    def apply(parameters: Parameters) = ShowSubscribers(parameters)
  }

  def showSubscribers(userId: Long, listId: Long) = ShowSubscribers().userId(userId).listId(listId)

  def showSubscribers(userId: Long, slug: String, ownerId: Long) = ShowSubscribers().userId(userId).slug(slug).ownerId(ownerId)

  def showSubscribers(userId: Long, slug: String, ownerName: String) = ShowSubscribers().userId(userId).slug(slug).ownerScreenName(ownerName)

  def showSubscribers(screenName: String, listId: Long) = ShowSubscribers().screenName(screenName).listId(listId)

  def showSubscribers(screenName: String, slug: String, ownerId: Long) = ShowSubscribers().screenName(screenName).slug(slug).ownerId(ownerId)

  def showSubscribers(screenName: String, slug: String, ownerName: String) = ShowSubscribers().screenName(screenName).slug(slug).ownerScreenName(ownerName)

  case class DestroySubscribers(parameters: Parameters = Map.empty) extends Resource[UserList, Required, DestroySubscribers](LISTS / "subscribers" / "destroy.json" <<) with ListId {
    def apply(parameters: Parameters) = DestroySubscribers(parameters)
  }

  def destroySubscribers(listId: Long) = DestroySubscribers(Map.empty).listId(listId)

  def destroySubscribers(slug: String, ownerId: Long) = DestroySubscribers(Map.empty).slug(slug).ownerId(ownerId)

  def destroySubscribers(slug: String, ownerName: String) = DestroySubscribers(Map.empty).slug(slug).ownerScreenName(ownerName)

}
