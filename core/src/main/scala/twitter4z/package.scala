import scalaz._
import Scalaz._

package object twitter4z {

  type TwitterResult[A] = ValidationNEL[TwitterException, A]

}
