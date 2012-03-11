package twitter4z.api.parameter

import scalaz._
import Scalaz._

sealed trait SearchResult

case object Mixed extends SearchResult {
  override def toString = "mixed"
}

case object Recent extends SearchResult {
  override def toString = "recent"
}

case object Popular extends SearchResult {
  override def toString = "popular"
}

object SearchResult extends SearchResultInstances

trait SearchResultInstances {

  implicit lazy val SearchResultShow = new Show[SearchResult] {
    def show(result: SearchResult) = result.toString.toList
  }

}
