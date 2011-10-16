package twitter4z.objects

case class Status(
  contributors: Option[List[ID]],
  coordinates: Option[Coordinates],
  createdAt: Date,
  entities: Option[Entities],
  favorited: Boolean,
  id: ID,
  inReplyToScreenName: Option[String],
  inReplyToStatusId: Option[ID],
  inReplyToUserId: Option[ID],
  place: Option[Place],
  retweetCount: Count,
  retweeted: Boolean,
  retweetedStatus: Option[Status],
  source: String,
  text: String,
  truncated: Boolean,
  user: User
)
