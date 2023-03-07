ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "PTR_Labs"
  )
//dependencies
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.16"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.4"

