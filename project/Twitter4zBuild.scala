import sbt._
import Keys._

object Dependencies {

  val scalaz = "org.scalaz" %% "scalaz-core" % "7.0-SNAPSHOT"

  val scalazConcurrent = "org.scalaz" %% "scalaz-concurrent" % "7.0-SNAPSHOT"

  val scalazEffect = "org.scalaz" %% "scalaz-effect" % "7.0-SNAPSHOT"

  val scalaj = "org.scalaj" %% "scalaj-http" % "0.2.9"

  val jsonScalaz = "net.liftweb" %% "lift-json-scalaz" % "2.5-SNAPSHOT"

  val jsonExt = "net.liftweb" %% "lift-json-ext" % "2.4"

  val specs = "org.specs2" %% "specs2" % "1.7.1" % "test"

  val swing = "org.scala-lang" % "scala-swing" % "2.9.1"

  val stm = "org.scala-tools" %% "scala-stm" % "0.4"

}

object Twitter4zBuild extends Build {

  import Dependencies._

  val buildSettings = Defaults.defaultSettings ++ Seq (
    version := "0.2",
    organization := "twitter4z",
    scalaVersion := "2.9.1",
    addCompilerPlugin("org.scala-tools.sxr" % "sxr_2.9.0" % "0.2.7"),
    scalacOptions <+= scalaSource in Compile map { "-P:sxr:base-directory:" + _.getAbsolutePath },
    scalacOptions += "-deprecation",
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
	scalaz,
	scalazConcurrent,
	scalazEffect,
	scalaj,
	jsonScalaz,
	jsonExt,
	specs
      )
    )
  )

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
