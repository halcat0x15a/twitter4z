package twitter4z.api.parameter

trait Lang { self: Parameter[Lang] =>
  lazy val lang = apply[String](LANG)
}
