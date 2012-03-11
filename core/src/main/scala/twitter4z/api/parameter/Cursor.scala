package twitter4z.api.parameter

trait Cursor { self: Parameter[Cursor] =>

  lazy val cursor = apply[Int](CURSOR)

}
