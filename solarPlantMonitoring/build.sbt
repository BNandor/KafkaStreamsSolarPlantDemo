name := "solar"

version := "0.1"

scalaVersion := "2.13.5"
val circeVersion = "0.12.3"

libraryDependencies += "org.apache.kafka" %% "kafka-streams-scala" % "2.7.0"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.7.0"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.12.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies ++= Seq("com.goyeau" %% "kafka-streams-circe" % "0.6.3")
libraryDependencies += "io.circe" %% "circe-core" % "0.13.0"
libraryDependencies += "io.circe" %% "circe-parser" % "0.13.0"
libraryDependencies += "io.circe" %% "circe-generic" % "0.13.0"
//lazy val circe = {
//  val version = "0.13.0"
//  Seq(
//    "io.circe" %% "circe-core" % version,
//    "io.circe" %% "circe-parser" % version,
//    "io.circe" %% "circe-java8" % version,
//    "io.circe" %% "circe-generic" % version
//  )
//}
