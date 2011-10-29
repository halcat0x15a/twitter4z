package twitter4z.objects

case class Ids(
  ids: List[Long],
  nextCursor: Int,
  previousCursor: Int
)
