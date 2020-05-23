name := """play-morphia"""
organization := "io.github.ducthienbui97"

description := "Play 2.7.x Module for Morphia http://mongodb.github.io/morphia/"

version := "1.1-2.7-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "3.12.0",
  "dev.morphia.morphia" % "core" % "1.5.8",
  "dev.morphia.morphia" % "validation" % "1.5.8",
  "org.easytesting" % "fest-assert" % "1.4" % "test"
)

crossPaths := false

publishTo := sonatypePublishToBundle.value