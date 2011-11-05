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

  implicit def TupleToParameter[A <% Seq[Parameter], B <% Seq[Parameter]](tuple: (A, B)): Seq[Parameter] = {
    val opt: Option[Seq[Parameter]] = Option(tuple).map {
      case (a, b) => a ++ b
    }
    opt.getOrElse(Seq.empty)
  }

  implicit def EitherToParameter[A <% Seq[Parameter], B <% Seq[Parameter]](either: Either[A, B]): Seq[Parameter] = {
    val opt: Option[Seq[Parameter]] = Option(either).map {
      case Right(r) => r
      case Left(l) => l
    }
    opt.getOrElse(Seq.empty)
  }

  implicit def ParameterToSeq[A <% Parameter](parameter: A): Seq[Parameter] = List(parameter)

  type Id = Long

  implicit def UserIdToId(userId: Long) = UserId(userId).right[ScreenName]

  implicit def StringToId(screenName: String) = ScreenName(screenName).left[UserId]

  type Statuses = List[StatusObject]

  type Users = List[User]

  type DirectMessages = List[DirectMessage]

}
