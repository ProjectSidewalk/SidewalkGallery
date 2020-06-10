package models.core

import slick.jdbc.PostgresProfile.api._

/**
 * Backend model object for a label object.
 *
 * @param canvasHeight  Height of the user's GSV canvas when they placed the label.
 * @param canvasWidth   Width of the user's GSV canvas when they placed the label.
 * @param canvasX       The center x-coordinate of the label placed by the user.
 * @param canvasY       The center y-coordinate of the label placed by the user.
 * @param description   Description that user provided of the label.
 * @param gsvPanoramaId GSV Panorama ID of the label.
 * @param heading       User's heading when they placed the label.
 * @param labelId       Unique ID that represents this label. Used as a primary key in the primary
 *                      Sidewalk database and can be used to access information in related tables.
 * @param labelTypeId   Label type of this label [1-7].
 * @param pitch         User's pitch when they placed the label.
 * @param severity      Severity of the label [1-5].
 * @param zoom          Zoom level the user was at when they placed the label. (Note that this is
 *                      the zoom level that we arbitrarily created in the front-end for core Project
 *                      Sidewalk).
 */
case class GalleryLabel(canvasHeight: Int,
                        canvasWidth: Int,
                        canvasX: Int,
                        canvasY: Int,
                        description: Option[String],
                        gsvPanoramaId: String,
                        heading: Float,
                        labelId: Int,
                        labelTypeId: Int,
                        pitch: Float,
                        severity: Option[Int],
                        zoom: Int)

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
  val labels = TableQuery[LabelTable]

  // TODO(aileenzeng): Define this method, change clients to use GalleryLabels instead of Labels.
  // def getLabels(labelTypeId: Int, count: Int): Future[Seq[GalleryLabel]] = {
  // }
}