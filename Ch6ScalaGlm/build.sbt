name := "Ch6ScalaGlm"

version := "0.1-SNAPSHOT"

scalacOptions ++= Seq(
  "-unchecked", "-deprecation", "-feature"
)

libraryDependencies  ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "darrenjw" %% "scala-glm" % "0.1-SNAPSHOT"
)

resolvers ++= Seq(
  "Sonatype Snapshots" at
    "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Releases" at
    "https://oss.sonatype.org/content/repositories/releases/",
  "Newcastle mvn repo" at "https://www.staff.ncl.ac.uk/d.j.wilkinson/mvn/"
)

scalaVersion := "2.12.1"

