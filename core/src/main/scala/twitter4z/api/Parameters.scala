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

  implicit def UserIdToEither(userId: Long): Either[UserId, ScreenName] = Left(UserId(userId))

  implicit def ScreenNameToEither(screenName: String): Either[UserId, ScreenName] = Right(ScreenName(screenName))

  implicit def ListIdToEither(listId: Long): Either[ListId, (Slug, Either[OwnerId, OwnerScreenName])] = Left(listId)

  implicit def OwnerIdToEither(ownerId: Long): Either[OwnerId, OwnerScreenName] = Left(ownerId)

  implicit def OwnerScreenNameToEither(ownerScreenName: String): Either[OwnerId, OwnerScreenName] = Right(ownerScreenName)

  implicit def SlugOwnerIdToEither(slugOwnerId: (String, Long)): Either[ListId, (Slug, Either[OwnerId, OwnerScreenName])] = slugOwnerId match {
    case (slug, ownerId) => Right((slug, ownerId))
  }

  implicit def SlugOwnerScreenNameToEither(slugOwnerScreenName: (String, String)): Either[ListId, (Slug, Either[OwnerId, OwnerScreenName])] = slugOwnerScreenName match {
    case (slug, ownerScreenName) => Right((slug, ownerScreenName))
  }

  implicit def UserIdAToEither(userIdA: Long): Either[UserIdA, ScreenNameA] = Left(userIdA)

  implicit def ScreenNameAToEither(screenNameA: String): Either[UserIdA, ScreenNameA] = Right(screenNameA)

  implicit def UserIdBToEither(userIdB: Long): Either[UserIdB, ScreenNameB] = Left(userIdB)

  implicit def ScreenNameBToEither(screenNameB: String): Either[UserIdB, ScreenNameB] = Right(screenNameB)

  implicit def SourceIdToEither(sourceId: Long): Either[SourceId, SourceScreenName] = Left(sourceId)

  implicit def SourceScreenNameToEither(sourceScreenName: String): Either[SourceId, SourceScreenName] = Right(sourceScreenName)

  implicit def TargetIdToEither(targetId: Long): Either[TargetId, TargetScreenName] = Left(targetId)

  implicit def TargetScreenNameToEither(targetScreenName: String): Either[TargetId, TargetScreenName] = Right(targetScreenName)

  type Statuses = List[StatusObject]

  type Users = List[User]

  type DirectMessages = List[DirectMessage]

}
