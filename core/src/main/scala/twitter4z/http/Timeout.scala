package twitter4z.http

sealed trait Timeout

case class Conn(time: Int) extends Timeout

case class Read(time: Int) extends Timeout
