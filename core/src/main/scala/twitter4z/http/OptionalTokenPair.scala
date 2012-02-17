package twitter4z.http

sealed trait OptionalTokenPair

case class TokenPair(consumer: Token[Consumer], access: Token[Access]) extends OptionalTokenPair

trait OptionalTokenPairImplicits {

  implicit case object DefaultTokenPair extends OptionalTokenPair

}
