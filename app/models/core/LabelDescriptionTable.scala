package models.core

import models.utils.MyPostgresProfile.api._
import play.api.libs.json.{JsObject, Json}

import scala.concurrent.Future
import scala.io.Source

case class LabelDescription(labelDescription: String,
                            labelDescriptionId: Int,
                            labelId: Int
                           )

class LabelDescriptionTable (tag: slick.lifted.Tag)
  extends Table[LabelDescription](tag, Some("sidewalk"), "label_description") {

  def labelDescription: Rep[String] = column[String]("label_description")
  def labelDescriptionId: Rep[Int] = column[Int]("label_description_id")
  def labelId: Rep[Int] = column[Int]("label_id")

  // Type of *: ProvenShape[LabelDescription]
  override def * = (labelDescription, labelDescriptionId,
    labelId) <> ((LabelDescription.apply _).tupled, LabelDescription.unapply)
}

object LabelDescriptionQuery extends TableQuery(new LabelDescriptionTable(_)) {
  val db = Database.forConfig("slick.dbs.default.db")
  val descriptions = TableQuery[LabelDescriptionTable]

  /**
   * Gets the description associated with some label with a given id.
   * @param labelId
   * @return
   */
  def getDescription(labelId: Int): Future[Seq[LabelDescription]] = {
    // Equivalent SQl query:
    //
    // SELECT description FROM label_description
    // WHERE label_description.label_id = labelId
    val query = LabelDescriptionQuery.filter(_.labelId === labelId).result
    db.run(query)
  }
}
