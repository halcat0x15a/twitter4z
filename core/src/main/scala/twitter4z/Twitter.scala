package twitter4z

import twitter4z.exception.TwitterExceptionInstances
import twitter4z.http.HTTP
import twitter4z.objects.JSON
import twitter4z.auth._
import twitter4z.api.API

class Twitter[A <: Authentication](val tokens: A) extends HTTP with OAuth/* with API */{

  type Tokens = A

}

object Twitter extends Twitter(Optional)
