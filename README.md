# Twitter4z

A Scalaz wrapper for Twitter API.

# Examples

## Usage

### Simple

    scala> import twitter4z.Twitter

    scala> Twitter.publicTimeline.unsafe

### Monadic

    scala> val w = for {
         |   statuses <- twitter.homeTimeline()
         |   _ <- twitter.updateStatus("@%s Hello!".format(statuses.head.user.screenName))()
         | } yield statuses

    scala> w.value

## Authorization

    scala> val consumer = Twitter.cosumer(key, secret)

    scala> val rtoken = Twitter.requestToken(consumer)
    
    scala> val url = Twitter.authorizationURI(rtoken)
    
    scala> val atoken = Twitter.accessToken(consumer, rtoken, "XXXXXXX")
    
    scala> val twitter = Twitter(atoken)

    scala> twitter.updateStatus("Twitter4z!")

## Option

    scala> twitter.homeTimeline.page(2).count(50)

# Source

http://halcat0x15a.github.com/twitter4z/core/target/scala-2.9.1/classes.sxr/index.html
