package twitter4z.auth

sealed trait TokenType

sealed trait Request extends TokenType

sealed trait Access extends TokenType
