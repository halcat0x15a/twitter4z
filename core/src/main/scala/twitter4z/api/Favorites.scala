package twitter4z.api

import scalaz._
import Scalaz._

import dispatch._

import twitter4z.objects._
import parameter._

trait Favorites { self: API =>

  case class Favorites(parameters: Parameters) extends Resource[List[Status], Required, Favorites](API_TWITTER_COM / "favorites.json" <<?) with Paging {
    def apply(parameters: Parameters) = Favorites(parameters)
  }

  lazy val favorites = Favorites(Map.empty)

  case class CreateFavorite(parameters: Parameters)(id: Long) extends Resource[Status, Required, CreateFavorite](FAVORITES / "create" / (id.shows |+| ".json") <<) {
    def apply(parameters: Parameters) = CreateFavorite(parameters)(id)
  }

  lazy val createFavorite = CreateFavorite(Map.empty)_

  case class DestroyFavorite(parameters: Parameters)(id: Long) extends Resource[Status, Required, DestroyFavorite](FAVORITES / "destroy" / (id.shows |+| ".json") <<) {
    def apply(parameters: Parameters) = DestroyFavorite(parameters)(id)
  }

  lazy val destroyFavorite = DestroyFavorite(Map.empty)_
}
