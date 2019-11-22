/** Installation for Protocol Buffers, via https://github.com/thesamet/sbt-protoc */

addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.25")

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.9.0"