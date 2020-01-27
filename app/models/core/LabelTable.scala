package models.core

import models.labels.LabelTable
import models.utils.MyPostgresProfile.api._
import play.api.libs.json.{JsObject, Json}

import scala.concurrent.Future
import scala.io.Source

case class GalleryLabel(gsvPanoramaId: String,
                        labelId: Int,
                        labelTypeId: Int)

/**
 * Backend model object for a label object.
 * @param gsvPanoramaId GSV Panorama ID of the label.
 * @param labelId       Unique ID that represents this label. Used as a primary key in the primary
 *                      Sidewalk database and can be used to access information in related tables.
 * @param labelTypeId   Label type of this label [1-7].
 */
case class Label(gsvPanoramaId: String,
                 labelId: Int,
                 labelTypeId: Int)

//
// NOTE: Several of these functions will be much more complicated because there
// will be several joins required to fetch all the data.
class LabelTable (tag: slick.lifted.Tag) extends Table[Label](tag, Some("sidewalk"), "label") {
  def labelId: Rep[Int]= column[Int]("label_id", O.PrimaryKey, O.AutoInc)
  def gsvPanoramaId: Rep[String] = column[String]("gsv_panorama_id")
  def labelTypeId: Rep[Int] = column[Int]("label_type_id")

  override def * = (gsvPanoramaId, labelId, labelTypeId) <> ((Label.apply _).tupled, Label.unapply)
}

object LabelQuery extends TableQuery(new LabelTable(_)) {
  val db = Database.forConfig("slick.dbs.default.db")
  val labels = TableQuery[LabelTable]

  // TODO(aileenzeng): Define this method, change clients to use GalleryLabels instead of Labels.
  // def getLabels(labelTypeId: Int, count: Int): Future[Seq[GalleryLabel]] = {
  // }
}