# Twitter4z

A Scalaz wrapper for Twitter API.

# Examples

## Usage

### Simple

```scala
import twitter4z.Twitter

Twitter.publicTimeline.unsafe
```

### Monadic

```scala
import scalaz._
import Scalaz._
import Validation.Monad._

val w = for {
  statuses <- twitter.homeTimeline()
   _ <- twitter.updateStatus("@%s Hello!".format(statuses.head.user.screenName))()
 } yield statuses

w.value
```

## Authorization

```scala
val consumer = Twitter.cosumer(key, secret)

val token = Twitter.requestToken(consumer)

val url = Twitter.authorizeURI(token)

val twitter = Twitter(consumer, token, "XXXXXXX")

twitter.updateStatus("Twitter4z!")
```

## Option

```
twitter.homeTimeline.page(2).count(50)
```

# Source

http://halcat0x15a.github.com/twitter4z/core/target/scala-2.9.1/classes.sxr/index.html
