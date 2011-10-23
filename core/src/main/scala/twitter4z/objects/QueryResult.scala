package twitter4z.objects

case class QueryResult(
  completedIn: Double,
  maxId: ID,
  nextPage: String,
  page: Int,
  query: String,
  refreshUrl: String,
  results: List[Tweet]
)
