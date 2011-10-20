package twitter4z

package object objects {

  type ID = Long

  type Indices = (Int, Int)

  type URL = java.net.URL

  type Count = Either[String, Int]

  type DateTime = org.joda.time.DateTime

}
