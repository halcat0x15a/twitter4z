package twitter4z.objects

case class Status(
  contributors: Option[List[Long]],
  coordinates: Option[Coordinates],
  createdAt: DateTime,
  entities: Option[Entities],
  favorited: Boolean,
  id: Long,
  inReplyToScreenName: Option[String],
  inReplyToStatusId: Option[Long],
  inReplyToUserId: Option[Long],
  place: Option[Place],
  retweetCount: Long,
  retweeted: Boolean,
  retweetedStatus: Option[Status],
  source: String,
  text: String,
  truncated: Boolean,
  user: User
)
