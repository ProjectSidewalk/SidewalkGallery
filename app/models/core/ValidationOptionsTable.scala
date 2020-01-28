package models.core

import models.utils.MyPostgresProfile.api._

case class ValidationOption(text: String,
                            validationOptionId: Int)

class ValidationOptionsTable (tag: slick.lifted.Tag) extends Table[ValidationOption](tag, Some("sidewalk"), "tag") {
  def text: Rep[String] = column[String]("text")
  def validationOptionId: Rep[Int] = column[Int]("validation_option_id")

  // Type: ProvenShape[ValidationOption]
  override def * = (text, validationOptionId) <> ((ValidationOption.apply _).tupled, ValidationOption.unapply)
}
