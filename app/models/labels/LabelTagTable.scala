package models.labels

import models.tags.TagTable
import models.utils.MyPostgresProfile.api._
import play.api.libs.json.{JsObject, Json}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * Backend model object for a tag object.
 */
case class LabelTag(labelId: Int,
                    labelTagId: Int,
                    tagId: Int)

case class LabelTagMetadata(description: String,
                            labelId: Int,
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
  val labels = TableQuery[LabelTable]
  val labelTags = TableQuery[LabelTagTable]
  val tags = TableQuery[TagTable]

  def getLabelTagsForLabel(labelId: Int): Seq[LabelTagMetadata] = {
    val query = for {
      lt <- labelTags if lt.labelId === labelId
      (t, lt) <- tags join labelTags on (_.tagId === _.tagId)
    } yield (t.description, labelId, lt.labelTagId, lt.tagId)

    val result = Await.result(db.run(query.result), Duration(10, "seconds"))
    result.map(x => LabelTagMetadata(x._1, x._2, x._3, x._4))

//    db.run(query.result)
  }

  /**
   * Converts a tag from its backend model representation to a JSON.
   * @param labelTag The label tag object to convert
   * @return      The JSON representation of this label tag.
   */
  def toTagMetadata(labelTag: LabelTagMetadata): JsObject = {
    Json.obj(
      "description" -> labelTag.description,
      "label_id" -> labelTag.labelId,
      "label_tag_id" -> labelTag.labelTagId,
      "tag_id" -> labelTag.tagId
    )
  }

}
