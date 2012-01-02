package twitter4z.objects

import net.liftweb.json._
import net.liftweb.json.scalaz.JsonScalaz._

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
  retweetCount: Count,
  retweeted: Boolean,
  retweetedStatus: Option[Status],
  source: String,
  text: String,
  truncated: Boolean,
  user: User
)

trait StatusJSON { self: JSON =>

  implicit object StatusJSONR extends JSONR[Status] {
    def read(json: JValue) = for {
      x <- field[Option[List[Long]]]("contributors")(json).map((Status.apply _).curried)
      x <- field[Option[Coordinates]]("coordinates")(json).map(x)
      x <- field[DateTime]("created_at")(json).map(x)
      x <- fieldOpt[Entities]("entities")(json).map(x)
      x <- field[Boolean]("favorited")(json).map(x)
      x <- field[Long]("id")(json).map(x)
      x <- field[Option[String]]("in_reply_to_screen_name")(json).map(x)
      x <- field[Option[Long]]("in_reply_to_status_id")(json).map(x)
      x <- field[Option[Long]]("in_reply_to_user_id")(json).map(x)
      x <- field[Option[Place]]("place")(json).map(x)
      x <- field[Count]("retweet_count")(json).map(x)
      x <- field[Boolean]("retweeted")(json).map(x)
      x <- fieldOpt[Status]("retweeted_status")(json).map(x)
      x <- field[String]("source")(json).map(x)
      x <- field[String]("text")(json).map(x)
      x <- field[Boolean]("truncated")(json).map(x)
      x <- field[User]("user")(json).map(x)
    } yield x
  }

}
