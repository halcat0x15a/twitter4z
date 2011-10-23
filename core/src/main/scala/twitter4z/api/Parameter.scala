package twitter4z.api

import scalaz._
import Scalaz._

trait Parameter extends NewType[(String, String)]

abstract class AbstractParameter[+A: Show](key: String) extends Parameter with Product1[A] {

  val value = key -> _1.shows

}

trait Parameters extends XParameters {

  sealed abstract class Unit(val value: String) extends NewType[String]
  case object Kilometers extends Unit("km")
  case object Miles extends Unit("mi")

  implicit def EitherToParameter(either: Either[Parameter, Parameter]): Parameter = either.fold(identity, identity)

  type Id = Either[ScreenName, UserId]

  implicit def UserIdToId(userId: Long) = UserId(userId).right[ScreenName]

  implicit def StringToId(screenName: String) = ScreenName(screenName).left[UserId]

}
