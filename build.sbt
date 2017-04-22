name := """winsbroker"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies += javaJdbc
libraryDependencies += cache
libraryDependencies += javaWs
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.39"
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "5.0.0"

lazy val myProject = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
