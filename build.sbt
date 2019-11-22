name := """scala-play-angular-seed"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  watchSources ++= (baseDirectory.value / "public/ui" ** "*").get
)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  guice,
  // jdbc,    /** No longer needed? */
  filters,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test,
  "com.h2database" % "h2" % "1.4.199",
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
  "org.postgresql" % "postgresql" % "42.2.0"
).map(_.force())

PB.targets in Compile := Seq(
  PB.gens.java -> (sourceManaged in Compile).value
)
