
name := """CrudAngularPlay"""

version := "1.0-SNAPSHOT"


libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3-M1",
  "org.webjars" % "bootstrap" % "2.3.1",
  "org.webjars" % "requirejs" % "2.1.11-1",
  "org.scalatest" % "scalatest_2.10" % "2.1.3" % "test"
)

play.PlayImport.PlayKeys.playDefaultPort := 9001

lazy val root = (project in file(".")).addPlugins(PlayScala)
