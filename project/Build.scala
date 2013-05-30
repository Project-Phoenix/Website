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
	"de.phoenix" % "library" % "0.0.1-SNAPSHOT",
	"asm" % "asm" % "3.3.1"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
   resolvers += "Phoenix Projext Nexus" at "http://meldanor.dyndns.org:8081/nexus/content/groups/public/"     
  )

}
