name := "Twitter4z"

version := "0.1"

organization := "twitter4z"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "6.0.3",
  "org.scalaj" %% "scalaj-http" % "0.2.9",
  "net.liftweb" %% "lift-json-scalaz" % "2.4-M4",
  "net.liftweb" %% "lift-json-ext" % "2.4-M4"
)

addCompilerPlugin("org.scala-tools.sxr" % "sxr_2.9.0" % "0.2.7")

scalacOptions <+= scalaSource in Compile map { "-P:sxr:base-directory:" + _.getAbsolutePath }

scalacOptions += "-deprecation"
