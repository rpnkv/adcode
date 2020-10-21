name := "quadcode"

version := "0.1"

scalaVersion := "2.12.8"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.1"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-testkit" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion
)

/*libraryDependencies += "com.twitter" %% "algebird-core" % "0.13.7"*/
libraryDependencies += "net.agkn" % "hll" % "1.6.0"
libraryDependencies += "com.google.guava" % "guava" % "29.0-jre"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.2.0",
  "org.scalatest" %% "scalatest" % "3.2.0",
  "org.mockito" %% "mockito-scala" % "1.11.1"
)

/*
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.10")*/
