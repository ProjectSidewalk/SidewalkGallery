package models.labels

import models.utils.DatabaseConfig
import play.api.libs.json.{JsObject, Json}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.io.Source

/**
 * Backend model object for a label object.
 * @param canvasHeight  Height of user's GSV canvas when they user placed the
 *                      label.
 * @param canvasWidth   Width of user's GSV canvas when they placed the
 *                      label.
 * @param canvasX       The center x-coordinate of the label placed by the user.
 * @param canvasY       The center y-coordinate of the label placed by the user.
 * @param description   Description that user provided of the label.
 * @param gsvPanoramaId GSV Panorama ID of the label.
 * @param heading       User's heading when they placed the label.
 * @param labelId       Unique ID that represents this label. Used as a primary
 *                      key in the primary Sidewalk database and can be used to
 *                      access information in related tables.
 * @param labelTypeId   Label type of this label [1-7].
 * @param pitch         User's pitch when they placed the label.
 * @param severity      Severity of the label [1-5].
 * @param zoom          Zoom level the user was at when they placed the label.
 *                      (Note that this is the zoom level that we arbitrarily
 *                      created in the front-end for core Project Sidewalk).
 */
case class Label(canvasHeight: Int,
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
 * Data access object representing the "label" table.
 * @param tag
 */
class LabelTable (tag: slick.lifted.Tag)
    extends Table[Label](tag, "label") {
  def canvasHeight: Rep[Int]  = column[Int]("canvas_height")
  def canvasWidth: Rep[Int] = column[Int]("canvas_width")
  def canvasX: Rep[Int] = column[Int]("canvas_x")
  def canvasY: Rep[Int] = column[Int]("canvas_y")
  def labelId: Rep[Int]= column[Int]("label_id", O.PrimaryKey, O.AutoInc)
  def gsvPanoramaId: Rep[String] = column[String]("gsv_panorama_id")
  def description: Rep[Option[String]] = column[Option[String]]("description")
  def severity: Rep[Option[Int]] = column[Option[Int]]("severity")
  def labelTypeId: Rep[Int] = column[Int]("label_type_id")
  def heading: Rep[Float] = column[Float]("heading")
  def pitch: Rep[Float] = column[Float]("pitch")
  def zoom: Rep[Int] = column[Int]("zoom")

  override def * = (canvasHeight, canvasWidth, canvasX, canvasY,
    description, gsvPanoramaId, heading, labelId, labelTypeId, pitch, severity,
    zoom) <> ((Label.apply _).tupled, Label.unapply)
}

/**
 * Object that runs queries to be performed on the table.
 */
object LabelQuery extends TableQuery(new LabelTable(_)) {
  val labels = TableQuery[LabelTable]

  /****************************************************************************
   * DB Requests
   ***************************************************************************/

  /**
   * Retrieves labels associated with a certain label type.
   * @param labelTypeId LabelTypeID to retrieve.
   * @param count       Maximum number of labels to retrieve.
   * @return            Seq of labels that were associated with this label.
   */
  def getLabels(labelTypeId: Int, count: Int): Future[Seq[Label]] = {
    // Equivalent SQL query:
    //
    // SELECT * from labels
    // WHERE label_type_id = count
    //
    val query = LabelQuery
      .filter(_.labelTypeId === labelTypeId)
      .take(count)
      .result
    DatabaseConfig.db.run(query)
  }

  /****************************************************************************
   * Data Conversion Utils (e.g., label --> json)
   ***************************************************************************/

  /**
   * Converts a label from its backend model representation to a JSON.
   * @param label The label object to convert
   * @return      The JSON representation of this label.
   */
  def toLabelJson(label: Label): JsObject = {
    // TODO (@aileenzeng): Move URL to a more secure function to avoid exposing
    //  the Google Maps API Key.

    val labelTags: Seq[JsObject] = Await.result(LabelTagQuery
      .getLabelTags(label.labelId), Duration(10, "seconds"))
      .map(LabelTagQuery.toTagMetadata)
      .map(LabelTagQuery.toTagJson)

    Json.obj(
      "canvas_height" -> label.canvasHeight,
      "canvas_width" -> label.canvasWidth,
      "canvas_x" -> label.canvasX,
      "canvas_y" -> label.canvasY,
      "description" -> label.description,
      "heading" -> label.heading,
      "image_url" -> getImageUrl(label),
      "label_id" -> label.labelId,
      "label_type_id" -> label.labelTypeId,
      "gsv_panorama_id" -> label.gsvPanoramaId,
      "pitch" -> label.pitch,
      "severity" -> label.severity,
      "tags" -> labelTags,
      "zoom" -> label.zoom
    )
  }

  /****************************************************************************
   * General Utils
   ***************************************************************************/
  /**
   * Hacky fix to generate the FOV for an image (sort of like zoom).
   * Determined experimentally.
   * @param label Label to retrieve the FOV for.
   */
  def getFov(label: Label): Double = {
    if (label.zoom == 1) {
      47.5
    } else if (label.zoom == 2) {
      52.5
    } else {
      57.5
    }
  }

  /**
   * Retrieves the static image of the label panorama from the Google Street
   * View Static API. Note that this returns the image of the panorama, but
   * doesn't actually include the label. More information here:
   * https://developers.google.com/maps/documentation/streetview/intro
   *
   * @param label Label to retrieve the static image of.
   * @return  Image URL that represents the background of the label.
   */
  def getImageUrl(label: Label): String = {
    val url = "https://maps.googleapis.com/maps/api/streetview?" +
      "pano=" + label.gsvPanoramaId +
      "&size=" + label.canvasWidth + "x" + label.canvasHeight +
      "&heading=" + label.heading +
      "&pitch=" + label.pitch +
      "&fov=" + getFov(label) +
      "&key=" + getGoogleMapsAPIKey()
    url
  }

  /**
   * Returns Google Maps API key from google_maps_api_key.txt (ask Mikey
   * Saugstad for the file if you don't have it).
   * @return  Google Maps API Key.
   */
  def getGoogleMapsAPIKey(): String = {
    val bufferedSource = Source.fromFile("google_maps_api_key.txt")
    val lines = bufferedSource.getLines()
    val key: String = lines.next()
    bufferedSource.close
    key
  }
}
