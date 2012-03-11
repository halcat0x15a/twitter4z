package twitter4z.api.parameter

trait Page { self: Parameter[Page] =>
  lazy val page = apply[Int](PAGE)
  lazy val sinceId = apply[Long](SINCE_ID)
  lazy val maxId = apply[Long](MAX_ID)
}
