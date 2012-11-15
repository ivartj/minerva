import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "Minerva"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "com.typesafe" % "play-plugins-mailer_2.9.1" % "2.0.4",
      "mysql" % "mysql-connector-java" % "5.1.18"
     
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
