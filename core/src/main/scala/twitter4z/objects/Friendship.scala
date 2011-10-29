package twitter4z.objects

case class Friendship(
  id: Long,
  screenName: String,
  name: String,
  connections: List[String]
)
