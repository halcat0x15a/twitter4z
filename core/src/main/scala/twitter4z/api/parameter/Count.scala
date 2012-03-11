package twitter4z.api.parameter

trait Count { self: Parameter[Count] =>
  lazy val count = apply[Int](COUNT)
}
