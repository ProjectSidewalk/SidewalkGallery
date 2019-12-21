package models.labels

import models.tags.TagTable
import models.utils.MyPostgresProfile.api._
import play.api.libs.json.{JsObject, Json}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await}

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
class LabelTagTable (tag: slick.lifted.Tag) extends Table[LabelTag](tag, "label_tags") {
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

  /**
   * TODO(@aileenzeng): Instead of returning a Seq[LabelTagMetadata], return a
   * Future[Seq[LabelTagMetadata]].
   *
   * Gets all the label tags associated with a label.
   * @param labelId Label to retrieve tags for.
   * @return  Sequence of tags associated with this label.
   */
  def getLabelTagsForLabel(labelId: Int): Seq[LabelTagMetadata] = {
    val result: Seq[(String, Int, Int, Int)] = queryTagsForLabel(labelId)
    result.map(x => LabelTagMetadata(x._1, x._2, x._3, x._4))
  }

  /**
   * Gets just the strings that are associated with this label.
   * @param labelId The label to retrieve tags for
   * @return  All tags that have been applied to this label.
   */
  def getTagDescriptionsForLabel(labelId: Int): Seq[String] = {
    val result: Seq[(String, Int, Int, Int)] = queryTagsForLabel(labelId)
    result.map(x => x._1)
  }

  /**
   * Gets just the ids that are associated with this label.
   * @param labelId The label to retrieve tags for
   * @return  All tags that have been applied to this label.
   */
  def getTagIdsForLabel(labelId: Int): Seq[Int] = {
    val result: Seq[(String, Int, Int, Int)] = queryTagsForLabel(labelId)
    result.map(x => x._3)
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

  /**
   * Helper that retrieves all the label tag data for a certain label.
   * @param labelId Label ID to retrieve data for.
   * @return  Tuple containing the tag information for each tag associated with the label. The
   *          ordering of the duple is (description, labelId, labelTagId, label).
   *
   *          * Description represents the text of the label.
   *          * LabelId is the id of the label that this tag was applied to.
   *          * LabelTagId is the that associated with the tags of this name. (e.g., "bumpy" may be
   *            have an ID of 4, "cracked" may have an ID of 5).
   *          * TagId is the unique ID of this particular tag.
   */
  private def queryTagsForLabel(labelId: Int): Seq[(String, Int, Int, Int)] = {
    // Equivalent SQL command:
    //
    // SELECT t.description, lt.labelId, lt.labelTagId, lt.label
    // FROM labelTags as 'lt' AND tags as 't'
    // WHERE lt.labelId = labelID
    //     AND t.tagId = lt.tagId
    //
    val query = for {
      lt <- labelTags if lt.labelId === labelId
      (t, lt) <- tags join labelTags on (_.tagId === _.tagId)
    } yield (t.description, lt.labelId, lt.labelTagId, lt.tagId)

    Await.result(db.run(query.result), Duration(10, "seconds"))
  }
}
