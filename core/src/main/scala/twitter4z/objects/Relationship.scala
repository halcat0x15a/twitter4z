package twitter4z.objects

case class Source(
  id: Long,
  screenName: String,
  following: Boolean,
  followedBy: Boolean,
  blocking: Boolean,
  canDm: Boolean,
  markedSpam: Boolean,
  allReplies: Boolean,
  wantRetweets: Boolean,
  notificationsEnabled: Boolean
)

case class Target(
  id: Long,
  screenName: String,
  following: Boolean,
  followedBy: Boolean
)

case class Relationship(
  source: Source,
  target: Target
)
