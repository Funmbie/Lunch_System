name := "RevisedLunchSystem"

version := "0.1"

scalaVersion := "2.12.7"

enablePlugins(JavaAppPackaging,JavaServerAppPackaging,sbtdocker.DockerPlugin,DockerComposePlugin)

lazy val sharedSettings = Seq(
  organization := "com.africasTalking",
  version      := "0.1.0",
  scalaVersion := "2.12.6",
  resolvers ++= Seq(
    "rediscala" at "http://dl.bintray.com/etaty/maven",
    "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"
  ),
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked"
  ),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.17",
    "com.typesafe.akka" %% "akka-testkit" % "2.5.17" % Test,
    "com.typesafe.akka" %% "akka-stream" % "2.5.16",
    "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.16" % Test,
    "com.typesafe.akka" %% "akka-http" % "10.1.5",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5" % Test,
    "io.spray" %%  "spray-json" % "1.3.4",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.5",
    "org.scalatest" %% "scalatest" % "3.0.5" % "test",
    "com.typesafe.akka" %% "akka-slf4j" % "2.5.17",
    "ch.qos.logback" % "logback-classic" % "1.2.3"
  )
)

lazy val lunch_system = (project in file(".")).aggregate(core,lunch,web)

lazy val core = (project in file("core")).settings(sharedSettings,
  libraryDependencies ++= Seq(

  )
)
lazy val lunch = (project in file("lunch")).dependsOn(core).settings(sharedSettings)
lazy val web = (project in file("web")).dependsOn(lunch).settings(sharedSettings)
