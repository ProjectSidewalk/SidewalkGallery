package models

import javax.inject.{Inject, Singleton}
import models.utils.DatabaseConfig
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import models.demo.Label

// Setup based off of this article: https://powerspace.tech/using-slick-in-production-dbfcbe29545c
@Singleton
class TestDAO @Inject()(protected val dbConfigProvider : DatabaseConfigProvider) {
  lazy val dbConfig = dbConfigProvider.get[JdbcProfile]

  def print(): Unit = {
    println("TestDAO: Printing (no db query)")
  }

  /**
   * Data access object representing the "label" table in the database.
   * @param tag
   */
  private class LabelTable (tag: slick.lifted.Tag) extends Table[Label](tag, "label") {
    def canvasHeight: Rep[Int]  = column[Int]("canvas_height")
    def canvasWidth: Rep[Int] = column[Int]("canvas_width")
    def canvasX: Rep[Int] = column[Int]("canvas_x")
    def canvasY: Rep[Int] = column[Int]("canvas_y")
    def labelId: Rep[Int]= column[Int]("label_id", O.PrimaryKey, O.AutoInc)
    def gsvPanoramaId: Rep[String] = column[String]("gsv_panorama_id")
    def description: Rep[Option[String]] = column[Option[String]]("description")
    def severity: Rep[Option[Int]] = column[Option[Int]]("severity")
    def labelTypeId: Rep[Int] = column[Int]("label_type_id")
    def heading: Rep[Float] = column[Float]("heading")
    def pitch: Rep[Float] = column[Float]("pitch")
    def zoom: Rep[Int] = column[Int]("zoom")

    // def lat = column[Float]("lat")
    // def lng = column[Float]("lng")

    override def * = (canvasHeight, canvasWidth, canvasX, canvasY,
      description, gsvPanoramaId, heading, labelId, labelTypeId,  /* lat, lng,*/ pitch, severity,
      zoom) <> ((Label.apply _).tupled, Label.unapply)
  }

  private val LabelQuery = TableQuery[LabelTable]

  def getSinglePanoId(): Future[Seq[Label]] = {
    val labelId: Int = 9
    val query = LabelQuery.filter(_.labelId === labelId).result
    // Run query the first time
    DatabaseConfig.db.run(query)

    // Run the same query again (should not create more clients).
    val duplicateQuery = LabelQuery.filter(_.labelId === labelId).result
    DatabaseConfig.db.run(duplicateQuery)

    val third = LabelQuery.filter(_.labelId === labelId).result
    DatabaseConfig.db.run(third)
  }
}