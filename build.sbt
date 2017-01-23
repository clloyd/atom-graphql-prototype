name := """sangria-akka-http-example"""
version := "0.1.0-SNAPSHOT"

description := "An example GraphQL server written with akka-http and sangria."

scalaVersion := "2.11.8"
scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.0.0-RC3",
  "org.sangria-graphql" %% "sangria-spray-json" % "0.3.2",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.11",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.squareup.okhttp" % "okhttp" % "2.4.0",
  "com.typesafe.play" %% "play-json" % "2.5.10",
  "org.sangria-graphql" %% "sangria-play-json" % "0.3.3",
  "com.gu" % "content-api-models-scala" % "10.22"
)

Revolver.settings
enablePlugins(JavaAppPackaging)

fork in run := true