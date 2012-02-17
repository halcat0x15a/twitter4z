package twitter4z.http

sealed trait TokenType

sealed trait Consumer extends TokenType

sealed trait Request extends TokenType

sealed trait Access extends TokenType
