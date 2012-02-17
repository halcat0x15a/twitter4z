package twitter4z.api

import scalaz._
import Scalaz._

import Optional._
import Parameters._

case class Paging(count: Optional[Int] = Default, page: Optional[Int] = Default) extends Parameters {

  val params = Params(COUNT, count) |+| Params(PAGE, page)

}

case class IdPaging(count: Optional[Int] = Default, sinceId: Optional[Long] = Default, maxId: Optional[Long] = Default, page: Optional[Int] = Default) extends Parameters {

  val params = Params(SINCE_ID, sinceId) |+| Params(MAX_ID, maxId) |+| Paging(count, page).params

}
