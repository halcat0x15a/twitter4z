package twitter4z.api

import twitter4z.http._
import twitter4z.objects._
import twitter4z.json._

trait DirectMessages { self: Parameters =>

  def directMessages(sinceId: SinceId = null, maxId: MaxId = null, count: Count = null, page: Page = null, includeEntities: IncludeEntities = null, skipStatus: SkipStatus = null)(implicit tokens: Some[Tokens]) = resource[List[DirectMessage]](get, "direct_messages", tokens, sinceId, maxId, count, page, includeEntities, skipStatus)

  def sentDirectMessages(sinceId: SinceId = null, maxId: MaxId = null, count: Count = null, page: Page = null, includeEntities: IncludeEntities = null)(implicit tokens: Some[Tokens]) = resource[List[DirectMessage]](get, "direct_messages/sent", tokens, sinceId, maxId, count, page, includeEntities)

  def destroyDirectMessage(id: Long, includeEntities: IncludeEntities = null)(implicit tokens: Some[Tokens]) = resource[DirectMessage](post, "direct_messages/destroy/" + id, tokens, includeEntities)

  def newDirectMessage(id: Id, text: Text, wrapLinks: WrapLinks = null)(implicit tokens: Some[Tokens]) = resource[DirectMessage](post, "direct_messages/new", tokens, id, text, wrapLinks)

  def showDirectMessage(id: Long)(implicit tokens: Some[Tokens]) = resource[DirectMessage](get, "direct_messages/show/" + id, tokens)

}
