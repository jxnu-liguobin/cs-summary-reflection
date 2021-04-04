val dottyVersion = "3.0.0-RC2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-examples",
    version := "0.1.0",

    scalaVersion := dottyVersion,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )
