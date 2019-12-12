package models.labels

import models.utils.MyPostgresProfile.api._

import scala.concurrent.Future
// import play.api.libs.json.JsObject

case class Label(description: String,
                 labelId: Int,
                 labelTypeId: Int,
                 gsvPanoramaId: String,
                 heading: Float,
                 // lat: Float,
                 // lng: Float,
                 pitch: Float,
                 zoom: Int)

class LabelTable (tag: slick.lifted.Tag) extends Table[Label](tag, "label") {
  def labelId= column[Int]("label_id", O.PrimaryKey, O.AutoInc)
  def gsvPanoramaId = column[String]("gsv_panorama_id")
  def description = column[String]("description")
  def labelTypeId = column[Int]("label_type_id")
  def heading = column[Float]("heading")
  def pitch = column[Float]("pitch")
  // def lat = column[Float]("lat")
  // def lng = column[Float]("lng")
  def zoom = column[Int]("zoom")

  override def * = (description, labelId, labelTypeId,
  gsvPanoramaId, heading, /* lat, lng,*/ pitch, zoom) <> ((Label.apply _).tupled, Label.unapply)
}

object LabelQuery extends TableQuery(new LabelTable(_)) {
  val db = Database.forConfig("slick.dbs.default.db")


  def getLabels(labelTypeId: Int, count: Int): Future[Seq[Label]] = {
    val query = LabelQuery.filter(_.labelTypeId === labelTypeId).result
//    query.map(label => Label(label.description, label.labelId, label.labelTypeId,
//      label.gsvPanoramaId, label.heading, label.lat, label.lng, label.pitch, label.zoom))
    db.run(query)
  }
}
