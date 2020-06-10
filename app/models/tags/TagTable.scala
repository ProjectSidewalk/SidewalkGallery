package models.tags

import models.utils.DatabaseConfig
import slick.jdbc.PostgresProfile.api._
import play.api.libs.json.{JsObject, Json}

import scala.concurrent.Future

/**
 * Backend model object for a tag object.
 */
case class Tag(labelTypeId: Int,
               tag: String,
               tagId: Int)

/**
 * Data access object representing the "label" table in the database.
 * @param tag
 */
class TagTable (tag: slick.lifted.Tag) extends Table[Tag](tag, "tag") {
  def labelTypeId: Rep[Int] = column[Int]("label_type_id")
  def description: Rep[String] = column[String]("tag")
  def tagId: Rep[Int]= column[Int]("tag_id", O.PrimaryKey, O.AutoInc)

  // def lat = column[Float]("lat")
  // def lng = column[Float]("lng")
  override def * = (labelTypeId, description, tagId) <> ((Tag.apply _).tupled, Tag.unapply)
}

/**
 * Object representing the queries that are performed on the table.
 */
object TagQuery extends TableQuery(new TagTable(_)) {

  /**
   * Retrieves tags belonging to a certain label type.
   * @param labelTypeId LabelTypeID to retrieve.
   * @return            Seq of labels that were associated with this label.
   */
  def getTags(labelTypeId: Int): Future[Seq[Tag]] = {
    val query = TagQuery.filter(_.labelTypeId === labelTypeId).result
    DatabaseConfig.db.run(query)
  }

  /**
   * Converts a label from its backend model representation to a JSON.
   * @param tag The label object to convert
   * @return      The JSON representation of this label.
   */
  def toTagMetadata(tag: Tag): JsObject = {
    Json.obj(
      "label_type_id" -> tag.labelTypeId,
      "tag_id" -> tag.tagId,
      "tag" -> tag.tag
    )
  }

}
