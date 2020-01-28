package models.core

import models.utils.MyPostgresProfile.api._

case class LabelSeverity(labelId: Int,
                         labelSeverityId: Int,
                         labelSeverity: Int)

class LabelSeverityTable (tag: slick.lifted.Tag) extends Table[LabelPoint](tag, Some("sidewalk"), "label_point"){
  def labelId: Rep[Int] = column[Int]("label_id")
  def labelSeverityId: Rep[Int] = column[Int]("label_severity_id")
  def labelSeverity: Rep[Int] = column[Int]("label_severity")

  // Type: ProvenShape[LabelSeverity]
  override def * = (labelId, labelSeverityId, labelSeverity) <> (
    (LabelSeverity.apply _).tupled, LabelSeverity.unapply)
}
