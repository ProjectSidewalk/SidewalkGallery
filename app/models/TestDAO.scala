package models

import scala.concurrent.{ ExecutionContext, Future }
import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

class DatabaseInitializer @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                                    (implicit executionContext: ExecutionContext)
                                    extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  def initDatabaseTables(): Unit = {
    println("Hello")
  }
}