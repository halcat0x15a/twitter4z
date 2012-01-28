package twitter4z.api

import scalaz._
import Scalaz._

import Parameters._

case class Paging(count: Optional[Int] = Default, page: Optional[Int] = Default) extends Parameter {

  val value = Seq(count(Count), page(Page))

}

case class IdPaging(count: Optional[Int] = Default, sinceId: Optional[Long] = Default, maxId: Optional[Long] = Default, page: Optional[Int] = Default) extends Parameter {

  val value = Seq(sinceId(SinceId), maxId(MaxId)) |+| Paging(count, page)

}
