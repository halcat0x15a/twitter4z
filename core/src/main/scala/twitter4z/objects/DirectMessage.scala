package twitter4z.objects

case class DirectMessage(
  createdAt: DateTime,
  id: Long,
  recipient: User,
  recipientId: Long,
  recipientScreenName: String,
  sender: User,
  senderId: Long,
  senderScreenName: String,
  text: String
)
