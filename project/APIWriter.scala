import sbt._

import net.liftweb.util.Html5

import scalaz._
import Scalaz._

object APIWriter extends Generator with HtmlParser {

  def write(dir: ManagedDirectory): Unit = {
    for (api <- parse("https://dev.twitter.com/docs/api")) {
      val resources = (api \\ "a").map(_ \ "@href" text).filter(_.startsWith("/docs/api/1/")).map("https://dev.twitter.com" +).toSet
      for (url <- (resources -- files(dir).map(_.name.replace("-", "/"))).par) {
        for (html <- parse(url)) {
	  "writing %s".format(url).println
	  IO.write(dir / url.replace("/", "-"), Html5.toString(html))
	}
      }
    }
  }

}
