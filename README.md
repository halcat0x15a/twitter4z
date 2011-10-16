# Twitter4z

A Scalaz wrapper for Twitter API.

# Examples

## Usage

    scala> import twitter4z._

    scala> import Twitter4z._
    
    scala> publicTimeline()

## Authorization

    scala> val rtoken = requestToken(key, secret)
    
    scala> val url = authorization(rtoken)
    
    scala> implicit val atoken = accessToken(rtoken, "XXXXXXX")
    
    scala> update("Twitter4z!")

## Read & Write
    
    scala> atoken.write("hoge")
    
    scala> implicit val atoken = readTokens("hoge")

## Option

    scala> homeTimeline(page = 2, count = 50)

# Source

http://halcat0x15a.github.com/twitter4z/target/scala-2.9.1.final/classes.sxr/index.html