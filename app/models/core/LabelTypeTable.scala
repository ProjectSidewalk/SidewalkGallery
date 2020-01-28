package models.core

import models.utils.MyPostgresProfile.api._

case class LabelType(description: String,
                     labelType: Int,
                     labelTypeId: Int)

class LabelTypeTable(tag: slick.lifted.Tag) extends Table[LabelTypeTable](tag, Some("sidewalk"), "label_tag") {
  def description: Rep[Int] = column[Int]("description")
  def labelType: Rep[Int] = column[Int]("label_type")
  def labelTypeId: Rep[Int] = column[Int]("label_type_id")

  // Type: ProvenShape[LabelTag]
  override def * = (description, labelType, labelTypeId) <> ((LabelType.apply _).tupled, LabelType.unapply)
}