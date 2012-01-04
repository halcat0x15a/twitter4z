package twitter4z.api

import scalaz._
import Scalaz._

case class Paging(count: Optional[Int] = Default, sinceId: Optional[Long] = Default, maxId: Optional[Long] = Default, page: Optional[Int] = Default) extends NewType[Seq[Option[(String, String)]]] {

  private lazy val fields: Seq[(String, Optional[String])] = Seq("count" -> count, "since_id" -> sinceId, "max_id" -> maxId, "page" -> page)

  val value = fields.map {
    case (key, optional) => key.some <|*|> optional.option
  }

}
