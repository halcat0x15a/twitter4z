package twitter4z.objects

case class Category(
  name: String,
  slug: String,
  size: Int,
  users: Option[List[User]]
)
