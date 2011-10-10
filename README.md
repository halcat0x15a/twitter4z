# Twitter4Z

A Scalaz wrapper for the Twitter API.

# Examples

## Authorization

    scala> val rtoken = requestToken("*key*", "*secret*")
    
    scala> val url = authorization(rtoken)
    
    **Open URL**
    
    scala> implicit val atoken = accessToken(rtoken, "*pin code*")
    
    scala> update("Twitter4Z!")

## Option

   scala> homeTimeline(page = 2, count = 50)
   
   scala> update("@*screen name* *text*", inReplyToStatusId = *status id*)
