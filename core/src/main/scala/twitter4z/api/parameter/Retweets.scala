package twitter4z.api.parameter

trait Device extends UserId { self: Parameter[Device] =>

  lazy val device = apply[Boolean](DEVICE)

  lazy val retweets = apply[Boolean](RETWEETS)

}
