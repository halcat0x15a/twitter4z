package twitter4z

import scalaz._
import Scalaz._

package object exception {

  type TwitterResult[A] = ValidationNEL[TwitterException, A]

}
