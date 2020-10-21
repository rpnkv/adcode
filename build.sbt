name := "quadcode"

version := "0.1"

scalaVersion := "2.12.8"

mainClass := Some("org.rpnkv.practice.iv.quadcode.web.Server")

test in assembly := {}

assemblyJarName in assembly := "quadcode.jar"

/*assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}*/

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.1"

libraryDependencies ++= Seq(
  "net.agkn" % "hll" % "1.6.0",
  "com.google.guava" % "guava" % "29.0-jre",
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % Test,
  "org.scalactic" %% "scalactic" % "3.2.0" % Test,
  "org.scalatest" %% "scalatest" % "3.2.0" % Test,
  "org.mockito" %% "mockito-scala" % "1.11.1" % Test
)