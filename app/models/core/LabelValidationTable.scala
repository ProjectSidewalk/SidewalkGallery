package models.core

import slick.jdbc.PostgresProfile.api._

case class LabelValidation(labelId: Int,
                           labelValidationId: Int,
                           validationResult: Int)

class LabelValidationTable (tag: slick.lifted.Tag) extends Table[LabelValidation](tag, Some("sidewalk"), "label_validation") {
  def labelId: Rep[Int] = column[Int]("label_id")
  def labelValidationId: Rep[Int] = column[Int]("label_validation_id")
  def validationResult: Rep[Int] = column[Int]("validation_result")

  // Type: ProvenShape[LabelValidation]
  override def * = (labelId, labelValidationId, validationResult) <> (
    (LabelValidation.apply _).tupled, LabelValidation.unapply)
}
