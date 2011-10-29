import sbt._
import Keys._

object Dependencies {

  val scalaz = "org.scalaz" %% "scalaz-core" % "6.0.3"

  val scalaj = "org.scalaj" %% "scalaj-http" % "0.2.9"

  val jsonScalaz = "net.liftweb" %% "lift-json-scalaz" % "2.4-M4"

  val jsonExt = "net.liftweb" %% "lift-json-ext" % "2.4-M4"

}

object Twitter4zBuild extends Build {

  import Dependencies._

  val buildSettings = Defaults.defaultSettings ++ Seq (
    name := "Twitter4z",
    version := "0.1",
    organization := "twitter4z",
    scalaVersion := "2.9.1",
    libraryDependencies += scalaz,
    addCompilerPlugin("org.scala-tools.sxr" % "sxr_2.9.0" % "0.2.7"),
    scalacOptions <+= scalaSource in Compile map { "-P:sxr:base-directory:" + _.getAbsolutePath },
    scalacOptions += "-deprecation"
  )

  lazy val twitter4z = Project (
    "twitter4z",
    file ("."),
    settings = buildSettings
  ) aggregate (core)

  lazy val core = Project(
    id = "twitter4z-core",
    base = file("core"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= Seq(
	scalaz,
	scalaj,
	jsonScalaz,
	jsonExt
      ),
      sourceGenerators in Compile <+= (sourceManaged in Compile, resourceDirectory in Compile) map {
        case (dir, resource) => Boilerplate.generateAPI(dir, resource) :+ Boilerplate.generateParameter(dir, resource)
      },
      excludeFilter in unmanagedResources := "parameters"
    )
  )

}
