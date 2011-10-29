package twitter4z.objects

case class UserList(
  description: String,
  fullName: String,
  id: Long,
  memberCount: Int,
  name: String,
  slug: String,
  subscriberCount: Int,
  uri: String,
  user: User,
  following: Boolean,
  mode: String
)
