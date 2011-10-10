package twitter4z.objects

case class Entities(
  urls: List[Url],
  userMentions: List[UserMention],
  hashtags: List[Hashtag]
)
