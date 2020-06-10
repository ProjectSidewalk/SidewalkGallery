name := """scala-play-angular-seed"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  watchSources ++= (baseDirectory.value / "public/ui" ** "*").get
)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  evolutions,
  guice,
  filters,
  "org.postgresql" % "postgresql" % "9.4.1212",             // For connecting to Postgres
  "com.h2database" % "h2" % "1.4.196" % "test",             // For connecting to a database
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",       // DB Connection Pooling
  "com.typesafe.play" %% "play-slick" % "4.0.2",            // Slick (for DB Access)
  "com.typesafe.slick" %% "slick" % "3.2.1",                // Slick (for DB Access)
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2", // Database evolutions
  "org.apache.logging.log4j" % "log4j-api" % "2.13.3",      // Error logging
  "org.apache.logging.log4j" % "log4j-core" % "2.13.3"      // Error logging
).map(_.force())

PB.targets in Compile := Seq(
  PB.gens.java -> (sourceManaged in Compile).value
)

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  // "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint", // Enable recommended additional warnings.
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver.
  // "-Ylog-classpath",  // Warn if there are conflicting dependencies.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-unused-import"  // Warn when unused imports
)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

javaOptions ++= Seq("-Xmx3072M", "-Xms2048M")
