lazy val commonSettings = Seq(
  organization := "com.xebia",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.8",
  name := "MowItNow"
)

lazy val commonDependencies = Seq(
  "org.scaldi" %% "scaldi" % "0.5.8",
  "org.scaldi" %% "scaldi-akka" % "0.5.8",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4.16",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.16"
)

lazy val logDependencies = Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)

// test dependencies
lazy val testDependencies = Seq(
  "org.scalatest"           %% "scalatest"                    % "2.2.6"     % Test,
  "org.mockito"             %  "mockito-core"                 % "1.9.5"     % Test
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(libraryDependencies ++= commonDependencies)
  .settings(libraryDependencies ++= logDependencies)
  .settings(libraryDependencies ++= testDependencies)