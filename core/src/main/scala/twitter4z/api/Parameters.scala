package twitter4z.api

import scalaz._
import Scalaz._

trait Parameters extends XParameters {

  case class Paging(count: Count = null, sinceId: SinceId = null, maxId: MaxId = null, page: Page = null) extends NewType[List[Parameter]] {
    val value = List(count, sinceId, maxId, page).filter(null !=)
  }

  sealed abstract class Unit(val value: String) extends NewType[String]
  case object Kilometers extends Unit("km")
  case object Miles extends Unit("mi")

  implicit def EitherToParameter(either: Either[Parameter, Parameter]): Parameter = either.fold(identity, identity)

  type Id = Either[ScreenName, UserId]

  implicit def UserIdToId(userId: Long) = UserId(userId).right[ScreenName]

  implicit def StringToId(screenName: String) = ScreenName(screenName).left[UserId]

}
