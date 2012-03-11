package twitter4z.api.parameter

trait Query extends Page { self: Parameter[Query] =>

  lazy val query = apply[String](QUERY)

}
