package twitter4z.objects

sealed trait Resize

case object Crop extends Resize

case object Fit extends Resize

case class Size(
  w: Int,
  h: Int,
  resize: Resize
)

case class Sizes(
  large: Size,
  medium: Size,
  small: Size,
  thumb: Size
)

sealed trait MediaType

case object Photo extends MediaType

case class Media(
  id: Long,
  mediaUrl: URL,
  url: URL,
  displayUrl: String,
  expandedUrl: URL,
  sizes: Sizes,
  mediaType: MediaType,
  indices: Indices
)

