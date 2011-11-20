# Twitter4z

A Scalaz wrapper for Twitter API.

# Examples

## Usage

    scala> import twitter4z.Twitter._

    scala> publicTimeline()

## Authorization

    scala> val rtoken = requestToken(key, secret)
    
    scala> val url = authorization(rtoken)
    
    scala> implicit val atoken = accessToken(rtoken, "XXXXXXX")
    
    scala> updateStatus("Twitter4z!")

## Read & Write
    
    scala> writeTokens(atoken("hoge"))
    
    scala> implicit val atoken = readTokens("hoge")

## Option

    scala> homeTimeline(page=2, count=50)

# Source

http://halcat0x15a.github.com/twitter4z/core/target/scala-2.9.1/classes.sxr/index.html
