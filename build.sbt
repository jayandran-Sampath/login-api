name := """login-api"""
organization := "com.user"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"

libraryDependencies += guice

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"

libraryDependencies += "org.scalatestplus" %% "mockito-4-6" % "3.2.15.0" % "test"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "test"
libraryDependencies += "com.github.t3hnar" %% "scala-bcrypt" % "4.3.0"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.user.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.user.binders._"
