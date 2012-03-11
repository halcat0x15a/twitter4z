package twitter4z.api.parameter

trait ListId { self: Parameter[ListId] =>

  lazy val listId = apply[Long](LIST_ID)

  lazy val slug = apply[String](SLUG)

  lazy val ownerId = apply[Long](OWNER_ID)

  lazy val ownerScreenName = apply[String](OWNER_SCREEN_NAME)

  def listId[A: ContainsListId](value: A): Self = value match {
    case id: Long => listId(id)
    case (slug: String, id: Long) => this.slug(slug).ownerId(id)
    case (slug: String, name: String) => this.slug(slug).ownerScreenName(name)
  }

}
