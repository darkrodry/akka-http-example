name := "akka-moc"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  // base dependency needed for all the examples
  "com.typesafe.akka" %% "akka-http" % "10.0.5",

  // needed for showing JSON integration
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.5"
)