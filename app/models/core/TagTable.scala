package models.core

import models.utils.MyPostgresProfile.api._

case class Tag(labelTypeId: Int,
               tag: String,
               tagId: Int)

class TagTable (tag: slick.lifted.Tag) extends Table[Tag](tag, Some("sidewalk"), "tag") {
  def labelTypeId: Rep[Int] = column[Int]("label_type_id")
  def labelTag: Rep[String] = column[String]("tag")
  def tagId: Rep[Int] = column[Int]("tag_id")

  // Type: ProvenShape[Tag]
  override def * = (labelTypeId, labelTag, tagId) <> ((Tag.apply _).tupled, Tag.unapply)
}
