package models.core

import slick.jdbc.PostgresProfile.api._

case class LabelPoint(alphaX: Double,
                      alphaY: Double,
                      canvasHeight: Int,
                      canvasWidth: Int,
                      canvasX: Int,
                      canvasY: Int,
                      heading: Double,
                      labelId: Int,
                      labelPointId: Int,
                      lat: Double,
                      lng: Double,
                      pitch: Double,
                      svImageX: Int,
                      svImageY: Int,
                      zoom: Int)

class LabelPointTable (tag: slick.lifted.Tag) extends Table[LabelPoint](tag, Some("sidewalk"), "label_point"){
  def alphaX: Rep[Double] = column[Double]("alpha_x")
  def alphaY: Rep[Double] = column[Double]("alpha_y")
  def canvasHeight: Rep[Int]  = column[Int]("canvas_height")
  def canvasWidth: Rep[Int] = column[Int]("canvas_width")
  def canvasX: Rep[Int] = column[Int]("canvas_x")
  def canvasY: Rep[Int] = column[Int]("canvas_y")
  def heading: Rep[Double] = column[Double]("heading")
  def labelId: Rep[Int] = column[Int]("label_id")
  def labelPointId: Rep[Int] = column[Int]("label_point_id")
  def lat: Rep[Double] = column[Double]("lat")
  def lng: Rep[Double] = column[Double]("lng")
  def pitch: Rep[Double] = column[Double]("pitch")
  def svImageX: Rep[Int] = column[Int]("sv_image_x")
  def svImageY: Rep[Int] = column[Int]("sv_image_y")
  def zoom: Rep[Int] = column[Int]("zoom")

  // Type: ProvenShape[LabelPoint]
  override def * = (alphaX, alphaY, canvasHeight, canvasWidth, canvasX, canvasY, heading, labelId,
    labelPointId, lat, lng, pitch, svImageX, svImageY, zoom) <> (
    (LabelPoint.apply _).tupled, LabelPoint.unapply)
}
