package models.core

import slick.jdbc.PostgresProfile.api._

case class LabelSeverity(labelId: Int,
                         labelSeverityId: Int,
                         labelSeverity: Int)

class LabelSeverityTable (tag: slick.lifted.Tag) extends Table[LabelSeverity](tag, Some("sidewalk"), "label_severity"){
  def labelId: Rep[Int] = column[Int]("label_id")
  def labelSeverityId: Rep[Int] = column[Int]("label_severity_id")
  def labelSeverity: Rep[Int] = column[Int]("label_severity")

  // Type: ProvenShape[LabelSeverity]
  override def * = (labelId, labelSeverityId, labelSeverity) <> (
    (LabelSeverity.apply _).tupled, LabelSeverity.unapply)
}
