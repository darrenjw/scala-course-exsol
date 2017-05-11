name := "ch9-simulacrum"

version := "0.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", 
	"-language:implicitConversions", "-language:higherKinds")

libraryDependencies  ++= Seq(
            "org.scalatest" %% "scalatest" % "3.0.1" % "test",
            "org.typelevel" %% "cats" % "0.9.0"
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)


addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

scalaVersion := "2.12.1"

