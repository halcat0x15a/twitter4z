package twitter4z.api.parameter

trait SourceTargetId { self: Parameter[SourceTargetId] =>

  lazy val sourceId = apply[Long](SOURCE_ID)

  lazy val sourceScreenName = apply[String](SOURCE_SCREEN_NAME)

  lazy val targetId = apply[Long](TARGET_ID)

  lazy val targetScreenName = apply[String](TARGET_SCREEN_NAME)

  def sourceTargetId[A: ContainsId, B: ContainsId](valueA: A, valueB: B) = {
    (valueA, valueB) match {
      case (sourceId: Long, targetId: Long) => this.sourceId(sourceId).targetId(targetId)
      case (sourceId: Long, targetName: String) => this.sourceId(sourceId).targetScreenName(targetName)
      case (sourceName: String, targetId: Long) => sourceScreenName(sourceName).targetId(targetId)
      case (sourceName: String, targetName: String) => sourceScreenName(sourceName).targetScreenName(targetName)
    }
  }

}
