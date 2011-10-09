package twitter4z.objects

case class Url(url: URL,
	       displayUrl: Option[String],
	       expandedUrl: Option[URL],
	       indices: Indices)
