package twitter4z.objects

case class Tweet(
  createdAt: String,
  fromUser: String,
  fromUserId: Long,
  id: Long,
  isoLanguageCode: String,
  profileImageUrl: URL,
  source: String,
  text: String,
  toUser: Option[String],
  toUserId: Option[Long]
)
