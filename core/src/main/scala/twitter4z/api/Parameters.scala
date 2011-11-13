package twitter4z.api

import scalaz._
import Scalaz._

trait Parameters extends XParameters {

  implicit def TupleToParameter[A <% Seq[Parameter], B <% Seq[Parameter]](tuple: (A, B)): Seq[Parameter] = Option(tuple).map {
    case (a, b) => a ++ b
  }.orZero

  implicit def EitherToParameter[A <% Seq[Parameter], B <% Seq[Parameter]](either: Either[A, B]): Seq[Parameter] = {
    val opt: Option[Seq[Parameter]] = Option(either).map {
      case Left(l) => l
      case Right(r) => r
    }
    opt.orZero
  }

  implicit def ParameterToSeq[A <% Parameter](parameter: A): Seq[Parameter] = List(parameter)

  type Id = Long

  implicit def UserIdToId(userId: Long) = UserId(userId).right[ScreenName]

  implicit def StringToId(screenName: String) = ScreenName(screenName).left[UserId]

  type Statuses = List[StatusObject]

  type Users = List[User]

  type DirectMessages = List[DirectMessage]

}
