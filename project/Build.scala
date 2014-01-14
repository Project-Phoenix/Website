import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "Website"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
	"com.sun.jersey" % "jersey-bundle" % "1.17.1",
	"asm" % "asm" % "3.3.1",
	"commons-codec" % "commons-codec" % "1.7",
	"com.fasterxml.jackson.jaxrs" % "jackson-jaxrs-json-provider" % "2.2.1",
	"com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.2.1",
	"joda-time" % "joda-time" % "2.3",
	"de.phoenix" % "library" % "0.0.1-SNAPSHOT"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
   resolvers += "Phoenix Projext Nexus" at "http://meldanor.dyndns.org:8081/nexus/content/groups/public/"     
  )

}
