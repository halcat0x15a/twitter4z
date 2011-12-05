import sbt._
import Keys._

object Dependencies {

  val scalaz = "org.scalaz" %% "scalaz-core" % "6.0.3"

  val scalaj = "org.scalaj" %% "scalaj-http" % "0.2.9"

  val jsonScalaz = "net.liftweb" %% "lift-json-scalaz" % "2.4-M4"

  val jsonExt = "net.liftweb" %% "lift-json-ext" % "2.4-M4"

  val specs = "org.specs2" %% "specs2" % "1.6.1"

  val specsScalaz = "org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test"

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
    scalacOptions += "-deprecation"
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
	scalaj,
	jsonScalaz,
	jsonExt,
	specs,
	specsScalaz
      ),
      sourceGenerators in Compile <+= (sourceManaged in Compile, resourceDirectory in Compile) map {
        case (dir, resource) => ObjectsGenerator.generate(dir, resource) ++ ParametersGenerator.generate(dir, resource) ++ APIGenerator.generate(dir, resource)
      },
      sourceGenerators in Test <+= (sourceManaged in Test, resourceDirectory in Compile) map {
        case (dir, resource) => SpecsGenerator.generate(dir, resource)
      },
      excludeFilter in unmanagedResources := "parameters"
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
