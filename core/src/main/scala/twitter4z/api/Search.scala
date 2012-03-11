package twitter4z.api

import dispatch._

import twitter4z.objects.Status

import parameter._

import scalaz._
import Scalaz._

import org.joda.time._
import org.joda.time.format._

import Units._
import SearchResult._

trait Search { self: API =>

  case class Search(parameters: Parameters) extends Resource[List[Status], Optional, Search](url("http://search.twitter.com/search.json") <<?) with Query with Lang {
    lazy val formatter = DateTimeFormat.forPattern("YYYY-MM-DD")
    def apply(parameters: Parameters) = Search(parameters)
    def geocode(lat: Double, long: Double, radius: Double, unit: Units) = this(GEOCODE) = List(lat, long, radius.shows |+| unit.shows).mkString(",")
    lazy val locale = apply[String](LOCALE)
    lazy val resultType = apply[SearchResult](RESULT_TYPE)
    lazy val rpp = apply[Int](RPP)
    def until(date: DateTime) = this(UNTIL) = formatter.print(date)
  }

  lazy val search = Search(Map.empty)

}
