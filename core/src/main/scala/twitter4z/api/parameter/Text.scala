package twitter4z.api.parameter

trait Text { self: Parameter[Text] =>
  lazy val text = apply[String](TEXT)
}
