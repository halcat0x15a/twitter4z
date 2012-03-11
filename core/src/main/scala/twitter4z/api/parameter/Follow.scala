package twitter4z.api.parameter

trait Follow extends UserId { self: Parameter[Follow] =>

  lazy val follow = apply[Boolean](FOLLOW)

}
