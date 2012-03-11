import sbt._
import Keys._

object Dependencies {

  val scalaj = "org.scalaj" %% "scalaj-http" % "0.2.9"

  val jsonScalaz = "net.liftweb" %% "lift-json-scalaz" % "2.4"

  val jsonExt = "net.liftweb" %% "lift-json-ext" % "2.4"

  val specs2 = "org.specs2" %% "specs2" % "1.8.2"

  val scalacheck = "org.scala-tools.testing" %% "scalacheck" % "1.9"

//  val specs2Scalaz = "org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test"

  val swing = "org.scala-lang" % "scala-swing" % "2.9.1"

  val stm = "org.scala-tools" %% "scala-stm" % "0.4"

  val dispatch = "net.databinder" %% "dispatch-oauth" % "0.8.7"

  lazy val dispatchLiftJson = uri("git://github.com/dispatch/dispatch-lift-json#0.1.0")

}

object Twitter4zBuild extends Build {

  import Dependencies._

  val buildSettings = Defaults.defaultSettings ++ Seq (
    version := "0.2",
    organization := "twitter4z",
    scalaVersion := "2.9.1",
    addCompilerPlugin("org.scala-tools.sxr" % "sxr_2.9.0" % "0.2.7"),
    scalacOptions <+= scalaSource in Compile map { "-P:sxr:base-directory:" + _.getAbsolutePath },
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked"
    ),
    resolvers += ScalaToolsSnapshots
  )

  lazy val twitter4z = Project(
    "twitter4z",
    file("."),
    settings = buildSettings
  ) aggregate (core, plugin)

  lazy val core = Project(
    "twitter4z-core",
    file("core"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= Seq(
	scalaj,
	dispatch,
	jsonScalaz,
	jsonExt,
	specs2,
	scalacheck
      )
    )
  ) dependsOn (dispatchLiftJson)

  lazy val plugin = Project(
    "twitter4z-plugin",
    file("plugin"),
    settings = buildSettings ++ Seq(
      sbtPlugin := true
    )
  ) dependsOn (core)

  lazy val tetris = Project(
    "twitter4z-tetris",
    file("tetris"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= Seq(
	swing,
	stm
      )
    )
  ) dependsOn (core)

}
