ThisBuild / version := "0.1.0-SNAPSHOT"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test
ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "PTR_Labs"
  )
