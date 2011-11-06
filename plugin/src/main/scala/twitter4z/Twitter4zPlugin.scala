package twitter4z

import sbt._
import Keys._

object Twitter4zPlugin extends Plugin {

  lazy val twitter4zSettings = Seq(
    accessTokenFile := file(".tokens"),
    auth <<= accessTokenFile map { file =>
      val requestToken = Twitter4z.requestToken(consumer)
      println(Twitter4z.authorization(requestToken))
      SimpleReader.readLine("pin> ") foreach { pin =>
 	Twitter4z.writeTokens(file)(Twitter4z.accessToken(requestToken, pin))
      }
    },
    homeTimeline <<= useTokens { implicit tokens =>
      Twitter4z.homeTimeline() foreach { _.foreach(status => println(status.user.screenName, status.text)) }
    },
    updateStatus <<= useTokens { implicit tokens =>
      SimpleReader.readLine("status> ") foreach { status =>
	import Twitter4z._
	Twitter4z.updateStatus(status)
      }
    }
  )

  val consumer = http.Token("nT2Lqz8628ntJ2o8FD0Q", "YEyRv21aOormY5NByIn6FCSRGoNOjVDKdp1kjSQa4gs")

  def useTokens[A](f: Twitter4z.Tokens => A) = accessTokenFile.map(file => f(Twitter4z.readTokens(file)))

  val accessTokenFile = SettingKey[File]("access-token-file")

  val auth = TaskKey[Unit]("auth")

  val homeTimeline = TaskKey[Unit]("home-timeline")

  val updateStatus = TaskKey[Unit]("update-status")

}
