package twitter4z.api.parameter

trait ListParameter { self: Parameter[ListParameter] =>
  lazy val name = apply[String](NAME)
  lazy val mode = apply[ListMode](MODE)
  lazy val description = apply[String](DESCRIPTION)
}
