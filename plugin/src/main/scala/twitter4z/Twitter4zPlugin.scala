package twitter4z

import sbt._
import Keys._
import jline._

object Twitter4zPlugin extends Plugin {

  val reader = new ConsoleReader

  lazy val twitter4zSettings = Seq(
    accessTokenFile := file(".tokens"),
    auth <<= accessTokenFile map { file =>
      val requestToken = Twitter.requestToken(consumer)
      println(Twitter.authorization(requestToken))
      SimpleReader.readLine("pin> ") foreach { pin =>
 	Twitter.writeTokens(file)(Twitter.accessToken(requestToken, pin))
      }
    },
    homeTimeline <<= useTokens { implicit tokens =>
      Twitter.homeTimeline().get foreach { statuses =>
	val size = statuses.map(_.user.screenName.size).max
	def printline() = println("-" * reader.getTermwidth)
        printline()
        statuses foreach { status =>
	  println(("| %" + -size + "s | %s").format(status.user.screenName, status.text))
	  printline()
        }
      }
    },
    updateStatus <<= useTokens { implicit tokens =>
      SimpleReader.readLine("status> ") foreach { status =>
	import Twitter._
	Twitter.updateStatus(status)
      }
    }
  )

  val consumer = http.Token("nT2Lqz8628ntJ2o8FD0Q", "YEyRv21aOormY5NByIn6FCSRGoNOjVDKdp1kjSQa4gs")

  def useTokens[A](f: http.Tokens => A) = accessTokenFile.map(file => f(Twitter.readTokens(file)))

  val accessTokenFile = SettingKey[File]("access-token-file")

  val auth = TaskKey[Unit]("auth")

  val homeTimeline = TaskKey[Unit]("home-timeline")

  val updateStatus = TaskKey[Unit]("update-status")

}
