package twitter4z.api

package object parameter {

  type ContainsId[A] = Contains[A, t[Long]#t[String]]

  type ContainsListId[A] = Contains[A, t[Long]#t[(String, Long)]#t[(String, Long)]]

  val COUNT = "count"

  val PAGE = "page"

  val SINCE_ID = "since_id"

  val MAX_ID = "max_id"

  val USER_ID = "user_id"

  val SCREEN_NAME = "screen_name"

  val STATUS = "status"

  val IN_REPLY_TO_STATUS_ID = "in_reply_to_status_id"

  val LAT = "lat"

  val LONG = "long"

  val PLACE_ID = "place_id"

  val DISPLAY_COORDINATES = "display_coordinates"

  val QUERY = "q"

  val GEOCODE = "geocode"

  val LANG = "lang"

  val LOCALE = "locale"

  val RESULT_TYPE = "result_type"

  val RPP = "rpp"

  val UNTIL = "until"

  val TEXT = "text"

  val CURSOR = "cursor"

  val USER_ID_A = "user_id_a"

  val USER_ID_B = "user_id_b"

  val SCREEN_NAME_A = "screen_name_a"

  val SCREEN_NAME_B = "screen_name_b"

  val SOURCE_ID = "source_id"

  val SOURCE_SCREEN_NAME = "source_screen_name"

  val TARGET_ID = "target_id"

  val TARGET_SCREEN_NAME = "target_screen_name"

  val FOLLOW = "follow"

  val RETWEETS = "retweets"

  val DEVICE = "device"

  val PAR_PAGE = "per_page"

  val LIST_ID = "list_id"

  val SLUG = "slug"

  val OWNER_ID = "owner_id"

  val OWNER_SCREEN_NAME = "owner_screen_name"

  // UnionTypes

  type ![A] = A => Nothing
  type !![A] = ![![A]]

  trait Disj { self =>
    type D
    type t[S] = Disj {
      type D = self.D with ![S]
    }
  }

  type t[T] = {
    type t[S] = (Disj { type D = ![T] })#t[S]
  }

  type or[T <: Disj] = ![T#D]

  type Contains[S, T <: Disj] = !![S] <:< or[T]
  type ∈[S, T <: Disj] = Contains[S, T]

  sealed trait Union[T] {
    val value: Any
  }

  case class Converter[S](s: S) {
    def union[T <: Disj](implicit ev: Contains[S, T]): Union[T] =
      new Union[T] {
        val value = s
      }
  }

  implicit def any2Converter[S](s: S): Converter[S] = Converter[S](s)

}
