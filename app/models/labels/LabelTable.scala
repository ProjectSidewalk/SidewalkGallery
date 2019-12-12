package models.labels

import java.sql.Timestamp

import models.utils.MyPostgresProfile.api._
// import play.api.libs.json.JsObject

case class Label(description: String,
                 labelId: Int,
                 labelTypeId: Int,
                 gsvPanoramaId: String,
                 heading: Double,
                 lat: Float,
                 lng: Float,
                 pitch: Double,
                 zoom: Int)

class LabelTable (tag: slick.lifted.Tag) extends Table[Label](tag, Some("sidewalk"), "label") {
  def labelId= column[Int]("label_id", O.PrimaryKey, O.AutoInc)
  def auditTaskId = column[Int]("audit_task_id")
  def missionId = column[Int]("mission_id")
  def gsvPanoramaId = column[String]("gsv_panorama_id")
  def labelTypeId = column[Int]("label_type_id")
  def photographerHeading = column[Float]("photographer_heading")
  def photographerPitch = column[Float]("photographer_pitch")
  def panoramaLat = column[Float]("panorama_lat")
  def panoramaLng = column[Float]("panorama_lng")
  def deleted = column[Boolean]("deleted")
  def temporaryLabelId = column[Option[Int]]("temporary_label_id")
  def timeCreated = column[Option[Timestamp]]("time_created")
  def tutorial = column[Boolean]("tutorial")
  def streetEdgeId = column[Int]("street_edge_id")

  override def * = ???
}

object LabelTable {
//  val db = play.api.db.slick.DB
//  val tagTable = TableQuery[LabelTable]


}
