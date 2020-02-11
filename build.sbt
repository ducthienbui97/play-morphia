name := """play-morphia"""
organization := "io.github.ducthienbui97"

description := "Play 2.8.x Module for Morphia http://mongodb.github.io/morphia/"

version := "1.0-2.8-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "3.12.0",
  "dev.morphia.morphia" % "core" % "1.5.8",
  "org.easytesting" % "fest-assert" % "1.4" % "test"
)

publishTo := sonatypePublishToBundle.value