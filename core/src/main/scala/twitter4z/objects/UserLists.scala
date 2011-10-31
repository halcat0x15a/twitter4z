package twitter4z.objects

case class UserLists(
  lists: List[UserList],
  nextCursor: Long,
  previousCursor: Long
)
