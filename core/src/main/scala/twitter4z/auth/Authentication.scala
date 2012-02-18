package twitter4z.auth

import twitter4z.http._

sealed trait Authentication

case class Required(consumer: Token[Consumer], access: Token[Access]) extends Authentication

case object Optional extends Authentication
