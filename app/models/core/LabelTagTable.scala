package models.core

import slick.jdbc.PostgresProfile.api._

case class LabelTag(labelTagId: Int,
                    labelId: Int,
                    tagId: Int)

class LabelTagTable(tag: slick.lifted.Tag) extends Table[LabelTag](tag, Some("sidewalk"), "label_tag") {
  def labelTagId: Rep[Int] = column[Int]("label_tag_id")
  def labelId: Rep[Int] = column[Int]("label_id")
  def tagId: Rep[Int] = column[Int]("tag_id")

  // Type: ProvenShape[LabelTag]
  override def * = (labelTagId, labelId, tagId) <> ((LabelTag.apply _).tupled, LabelTag.unapply)
}