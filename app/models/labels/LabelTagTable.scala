package models.labels

import models.utils.MyPostgresProfile.api._
import play.api.libs.json.{JsObject, Json}

/**
 * Backend model object for a tag object.
 */
case class LabelTag(labelId: Int,
                    labelTagId: Int,
                    tagId: Int)

/**
 * Data access object representing the "label_tag" table in the database.
 * @param tag
 */
class LabelTagTable (tag: slick.lifted.Tag) extends Table[LabelTag](tag, "label_tag") {
  def labelId: Rep[Int] = column[Int]("label_id")
  def labelTagId: Rep[Int]= column[Int]("label_tag_id", O.PrimaryKey, O.AutoInc)
  def tagId: Rep[Int] = column[Int]("tag_id")

  override def * = (labelId, labelTagId, tagId) <> ((LabelTag.apply _).tupled, LabelTag.unapply)
}

/**
 * Object representing the queries that are performed on the table.
 */
object LabelTagQuery extends TableQuery(new LabelTagTable(_)) {
  val db = Database.forConfig("slick.dbs.default.db")

  /**
   * Converts a tag from its backend model representation to a JSON.
   * @param labelTag The label tag object to convert
   * @return      The JSON representation of this label tag.
   */
  def toTagMetadata(labelTag: LabelTag): JsObject = {
    Json.obj(
      "label_id" -> labelTag.labelId,
      "label_tag_id" -> labelTag.labelTagId,
      "tag_id" -> labelTag.tagId
    )
  }

}
