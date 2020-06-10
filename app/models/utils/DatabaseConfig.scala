package models.utils

import javax.inject.Singleton

// A Singleton configuration for a Database. All queries should be using this to create a new DB.
// https://docs.scala-lang.org/tour/singleton-objects.html

@Singleton
object DatabaseConfig {
  val driver = slick.driver.PostgresDriver

  import driver.api._

  // lazy val means that this db variable is just a Singleton Object.
  // TODO(aileenzeng)[#9]: Potentially add HikariCP parameters and see if that does
  //  anything to fix the connection pooling problems.
  lazy val db = Database.forConfig("slick.dbs.default.db")
}