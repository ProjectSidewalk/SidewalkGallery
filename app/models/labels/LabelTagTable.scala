package models.labels

import models.tags.TagTable
import models.utils.DatabaseConfig
import slick.jdbc.PostgresProfile.api._
import play.api.libs.json.{JsObject, Json}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * Backend model object for a tag object.
 */
case class LabelTag(labelId: Int,
                    labelTagId: Int,
                    tagId: Int)

case class LabelTagMetadata(labelId: Int,
                            labelTagId: Int,
                            tagDescription: String,
                            tagId: Int)

/**
 * Data access object representing the "label_tag" table.
 * @param tag
 */
class LabelTagTable (tag: slick.lifted.Tag) extends Table[LabelTag](tag, "label_tags") {
  def labelId: Rep[Int] = column[Int]("label_id")
  def labelTagId: Rep[Int]= column[Int]("label_tag_id", O.PrimaryKey, O.AutoInc)
  def tagId: Rep[Int] = column[Int]("tag_id")

  override def * = (labelId, labelTagId, tagId) <> ((LabelTag.apply _).tupled, LabelTag.unapply)
  }

/**
 * Object that runs queries to be performed on the table.
 */
object LabelTagQuery extends TableQuery(new LabelTagTable(_)) {
  val labels = TableQuery[LabelTable]
  val labelTags = TableQuery[LabelTagTable]
  val tags = TableQuery[TagTable]

  /****************************************************************************
   * DB Requests
   ***************************************************************************/

  /**
   * Helper that retrieves all the label tag data for a certain label.
   * @param labelId Label ID to retrieve data for.
   * @return  Tuple containing the tag information for each tag associated with
   *          the label. The ordering of the tuple is (description, labelId,
   *          labelTagId, label).
   *
   *          * Description represents the text of the label.
   *          * LabelId is the id of the label that this tag was applied to.
   *          * LabelTagId is the that associated with the tags of this name.
   *            (e.g., "bumpy" may be have an ID of 4, "cracked" may have an ID
   *            of 5).
   *          * TagId is the unique ID of this particular tag.
   */
   def getLabelTags(labelId: Int): Future[Seq[(Int, Int, String, Int)]] = {
    // Equivalent SQL command:
    //
    // SELECT t.description, lt.labelId, lt.labelTagId, lt.label
    // FROM labelTags as 'lt' AND tags as 't'
    // INNER JOIN tag ON tag.tag_id = label_tags.tag_id
    // WHERE lt.labelId = labelID
    //

    val filteredLabelTags = labelTags.filter(_.labelId === labelId)

    val query = for {
      (lt, t) <- filteredLabelTags join tags on (_.tagId === _.tagId)
    } yield (lt.labelId, lt.labelTagId, t.description, lt.tagId)

    DatabaseConfig.db.run(query.result)
  }

  /****************************************************************************
   * Data Conversion Utils (e.g., label --> json)
   ***************************************************************************/
  def toTagMetadata(label: (Int, Int, String, Int)): LabelTagMetadata = {
    LabelTagMetadata(label._1, label._2, label._3, label._4)
  }

  /**
   * Converts a tag from its backend model representation to a JSON.
   * @param labelTag The label tag object to convert
   * @return      The JSON representation of this label tag.
   */
  def toTagJson(labelTag: LabelTagMetadata): JsObject = {
    Json.obj(
      "label_id" -> labelTag.labelId,
      "label_tag_id" -> labelTag.labelTagId,
      "tag_description" -> labelTag.tagDescription,
      "tag_id" -> labelTag.tagId,
    )
  }
}
