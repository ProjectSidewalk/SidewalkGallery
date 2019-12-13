package models.labels

import models.utils.MyPostgresProfile.api._
import play.api.libs.json.{JsObject, Json}

import scala.concurrent.Future
import scala.io.Source

case class Label(canvasHeight: Int,
                 canvasWidth: Int,
                 canvasX: Int,
                 canvasY: Int,
                 description: Option[String],
                 gsvPanoramaId: String,
                 heading: Float,
                 labelId: Int,
                 labelTypeId: Int,
                 // lat: Float,
                 // lng: Float,
                 pitch: Float,
                 severity: Option[Int],
                 zoom: Int)

class LabelTable (tag: slick.lifted.Tag) extends Table[Label](tag, "label") {
  def canvasHeight = column[Int]("canvas_height")
  def canvasWidth = column[Int]("canvas_width")
  def canvasX = column[Int]("canvas_x")
  def canvasY = column[Int]("canvas_y")
  def labelId= column[Int]("label_id", O.PrimaryKey, O.AutoInc)
  def gsvPanoramaId = column[String]("gsv_panorama_id")
  def description = column[Option[String]]("description")
  def severity: Rep[Option[Int]] = column[Option[Int]]("severity")
  def labelTypeId = column[Int]("label_type_id")
  def heading = column[Float]("heading")
  def pitch = column[Float]("pitch")
  // def lat = column[Float]("lat")
  // def lng = column[Float]("lng")
  def zoom = column[Int]("zoom")

  override def * = (canvasHeight, canvasWidth, canvasX, canvasY,
    description, gsvPanoramaId, heading, labelId, labelTypeId,  /* lat, lng,*/ pitch, severity,
    zoom) <> ((Label.apply _).tupled, Label.unapply)
}

object LabelQuery extends TableQuery(new LabelTable(_)) {
  val db = Database.forConfig("slick.dbs.default.db")


  def getLabels(labelTypeId: Int, count: Int): Future[Seq[Label]] = {
    val query = LabelQuery.filter(_.labelTypeId === labelTypeId).result
//    query.map(label => Label(label.description, label.labelId, label.labelTypeId,
//      label.gsvPanoramaId, label.heading, label.lat, label.lng, label.pitch, label.zoom))
    db.run(query)
  }

  /**
   * TODO(@aileenzeng): Move URL to a more secure function to avoid exposing the Google Maps
   * API Key.
   *
   * Converts a label from its backend model representation to a JSON.
   * @param label The label object to convert
   * @return      The JSON representation of this label.
   */
  def toLabelMetadata(label: Label): JsObject = {
    Json.obj("label_id" -> label.labelId,
      "gsv_panorama_id" -> label.gsvPanoramaId,
      "severity" -> label.severity,
      "label_type_id" -> label.labelTypeId,
      "heading" -> label.heading,
      "pitch" -> label.pitch,
      "url" -> getImageUrl(label),
      "zoom" -> label.zoom
    )
  }

  def getImageUrl(label: Label): String = {
    val url = "https://maps.googleapis.com/maps/api/streetview?" +
      "pano=" + label.gsvPanoramaId +
      "&size=" + label.canvasWidth + "x" + label.canvasHeight +
      "&heading=" + label.heading +
      "&pitch=" + label.pitch +
      "&key=" + getGoogleMapsAPIKey()
    url
  }

  /**
   * Read in Google Maps API key from google_maps_api_key.txt (ask Mikey Saugstad for the file if you don't have it).
   *
   * @return
   */
  def getGoogleMapsAPIKey(): String = {
    val bufferedSource = Source.fromFile("google_maps_api_key.txt")
    val lines = bufferedSource.getLines()
    val key: String = lines.next()
    bufferedSource.close
    key
  }
}
