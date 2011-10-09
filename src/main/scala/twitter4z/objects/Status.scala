package twitter4z.objects

case class Coordinates(coordinates: (Double, Double), coordinatesType: String)

case class Status(contributors: Option[List[ID]],
		  coordinates: Option[Coordinates],
		  createdAt: String,
		  entities: Entities,
		  favorited: Boolean,
		  id: ID,
		  inReplyToScreenName: Option[String],
		  inReplyToStatusId: Option[ID],
		  inReplyToUserId: Option[ID],
		  place: Option[Place],
		  retweetCount: Count,
		  retweeted: Boolean,
		  source: String,
		  text: String,
		  truncated: Boolean,
		  user: User)
