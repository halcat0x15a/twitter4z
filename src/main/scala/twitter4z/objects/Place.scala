package twitter4z.objects

sealed trait PlaceType

case object Poi extends PlaceType

case object Neighborhood extends PlaceType

case object City extends PlaceType

case object Admin extends PlaceType

case object Country extends PlaceType

case class Place(
  country: String,
  countryCode: String,
  fullName: String,
  id: String,
  name: String,
  placeType: PlaceType,
  url: URL
)
