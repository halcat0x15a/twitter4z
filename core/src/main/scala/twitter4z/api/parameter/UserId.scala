package twitter4z.api.parameter

trait UserId { self: Parameter[UserId] =>
  lazy val userId = apply[Long](USER_ID)
  lazy val screenName = apply[String](SCREEN_NAME)
  def userId[A: ContainsId](value: A): Self = {
    value match {
      case id: Long => userId(id)
      case name: String => screenName(name)
    }
  }
}
