package twitter4z.api.parameter

trait UserIdAB { self: Parameter[UserIdAB] =>
  lazy val userIdA = apply[Long](USER_ID_A)
  lazy val userIdB = apply[Long](USER_ID_B)
  lazy val screenNameA = apply[String](SCREEN_NAME_A)
  lazy val screenNameB = apply[String](SCREEN_NAME_B)
  def userIdAB[A: ContainsId, B: ContainsId](valueA: A, valueB: B) = {
    (valueA, valueB) match {
      case (idA: Long, idB: Long) => userIdA(idA).userIdB(idB)
      case (idA: Long, nameB: String) => userIdA(idA).screenNameB(nameB)
      case (nameA: String, idB: Long) => screenNameA(nameA).userIdB(idB)
      case (nameA: String, nameB: String) => screenNameA(nameA).screenNameB(nameB)
    }
  }
}
