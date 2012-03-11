package twitter4z.objects

case class CursorList[A](
  list: List[A],
  nextCursor: Long,
  previousCursor: Long
)
