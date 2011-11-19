package twitter4z.objects

case class Place(
  country: String,
  countryCode: String,
  fullName: String,
  id: String,
  name: String,
  placeType: Place.Type,
  url: URL
)

object Place {
  
  sealed trait Type

  case object Poi extends Type

  case object Neighborhood extends Type

  case object City extends Type

  case object Admin extends Type

  case object Country extends Type

}
