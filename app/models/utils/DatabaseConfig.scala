package models.utils

import javax.inject.Singleton

// A Singleton configuration for a Database. We should only be creating one
// instance of this.
// https://docs.scala-lang.org/tour/singleton-objects.html
@Singleton
object DatabaseConfig {
  val driver = slick.driver.PostgresDriver

  import driver.api._

  // lazy val means that this db variable is just a Singleton Object.
  // TODO(aileenzeng)[#9]: Parameters are set in application.conf under the
  //   slick.dbs.default.db object.
  lazy val db = Database.forConfig("slick.dbs.default.db")
}