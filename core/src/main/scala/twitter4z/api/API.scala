package twitter4z.api

import scalaz._
import Scalaz._

import twitter4z.http._
import twitter4z.auth.OAuth
import twitter4z.objects.JSON

trait API extends Timelines with Tweets with OptionalInstances with IdInstances with ParameterSyntax { self: HTTP with OAuth with JSON =>

//  def resource[A](method: Method, url: String, parameters: (String, String)*)(implicit json: JSONR[A], tokenPair: OptionalTokenPair) = oauth(method(url).params(parameters.toList), tokenPair).process(parseJValue[A])

}
