package twitter4z

import scalaz._
import Scalaz._

package object exception {

  type TwitterExceptions = NonEmptyList[TwitterException]

  type TwitterResult[A] = Validation[TwitterExceptions, A]

  implicit lazy val TwitterExceptionShow: Show[TwitterException] = shows {
    _.exception match {
      case throwable: Throwable => throwable.getMessage
      case other => other.toString
    }
  }

}
