package twitter4z.objects

case class Profile(backgroundColor: String,
		   backgroundImageURL: URL,
		   backgroundTile: Boolean,
		   imageURL: URL,
		   linkColor: String,
		   sidebarBorderColor: String,
		   sidebarFillColor: String,
		   textColor: String,
		   useBackgroundImage: Boolean)

case class User(contributorsEnabled: Boolean,
		createdAt: String,
		description: Option[String],
		favouritesCount: Int,
		followRequestSent: Option[Boolean],
		followersCount: Int,
		friendsCount: Int,
		geoEnabled: Boolean,
		id: ID,
		isTranslator: Boolean,
		lang: String,
		listedCount: Int,
		location: Option[String],
		name: String,
		profile: Profile,
		screenName: String,
		showAllInlineMedia: Boolean)
