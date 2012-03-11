package twitter4z.api

import scalaz._
import Scalaz._

import twitter4z.objects._
import parameter._

trait DirectMessages { self: API =>

  case class DirectMessages(parameters: Parameters) extends Resource[List[DirectMessage], Required, DirectMessages](API_TWITTER_COM / "direct_messages.json" <<?) with Paging {
    def apply(parameters: Parameters) = DirectMessages(parameters)
  }

  lazy val directMessages = DirectMessages(Map.empty)

  case class SentDirectMessages(parameters: Parameters) extends Resource[List[DirectMessage], Required, SentDirectMessages](DIRECT_MESSAGES / "sent.json" <<?) with Paging {
    def apply(parameters: Parameters) = SentDirectMessages(parameters)
  }

  lazy val sentDirectMessages = SentDirectMessages(Map.empty)

  case class DestroyDirectMessage(parameters: Parameters)(id: Long) extends Resource[DirectMessage, Required, DestroyDirectMessage](DIRECT_MESSAGES / "destroy" / (id.shows |+| ".json") <<) {
    def apply(parameters: Parameters) = DestroyDirectMessage(parameters)(id)
  }

  lazy val destroyDirectMessage = DestroyDirectMessage(Map.empty)_

  case class NewDirectMessage(parameters: Parameters) extends Resource[DirectMessage, Required, NewDirectMessage](DIRECT_MESSAGES / "new.json" <<) with UserId {
    def apply(parameters: Parameters) = NewDirectMessage(parameters)
    lazy val text = apply[String](TEXT)
  }

  def newDirectMessages[A: ContainsId](value: A) = NewDirectMessage(Map.empty).userId(value)

  case class ShowDirectMessage(parameters: Parameters)(id: Long) extends Resource[DirectMessage, Required, ShowDirectMessage](DIRECT_MESSAGES / "show" / (id.shows |+| ".json") <<?) {
    def apply(parameters: Parameters) = ShowDirectMessage(parameters)(id)
  }

  lazy val showDirectMessage = ShowDirectMessage(Map.empty)_

}
