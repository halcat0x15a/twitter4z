package twitter4z.api

import twitter4z.objects._
import twitter4z.http._
import twitter4z.json._

trait Search { self: Parameters =>

  def search(q: Query, geocode: Geocode = null, lang: Lang = null, locale: Locale = null, page: Page = null, resultType: ResultType = null, rpp: ReturnPerPage = null, showUser: ShowUser = null, until: Until = null, sinceId: SinceId = null, includeEntities: IncludeEntities = null, withTwitterUserId: WithTwitterUserId = null) = resource[QueryResult](get, "http://search.twitter.com/search", None, q, geocode, lang, locale, page, resultType, rpp, showUser, until, sinceId, includeEntities, withTwitterUserId)

}
