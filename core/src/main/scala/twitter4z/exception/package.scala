package twitter4z

import scalaz._
import Scalaz._

package object exception {

  type TwitterExceptions = NonEmptyList[TwitterException]

  type TwitterResult[A] = Validation[TwitterExceptions, A]

}
