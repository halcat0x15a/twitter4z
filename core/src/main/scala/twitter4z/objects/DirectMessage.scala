package twitter4z.objects

case class DirectMessage(
  createdAt: DateTime,
  id: ID,
  recipient: User,
  recipientId: ID,
  recipientScreenName: String,
  sender: User,
  senderId: ID,
  senderScreenName: String,
  text: String
)
