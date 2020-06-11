package models.validations

import models.utils.DatabaseConfig
import slick.jdbc.PostgresProfile.api._

case class GalleryValidation(galleryValidationId: Int,
                             labelId: Int,
                             validationResult: Int)

/**
 * Data access object representing the "gallery_validations" table.
 * @param tag
 */
class GalleryValidationTable (tag: slick.lifted.Tag)
    extends Table[GalleryValidation](tag, "gallery_validations") {
  def galleryValidationId: Rep[Int] = column[Int]("gallery_validation_id",
      O.PrimaryKey, O.AutoInc)
  def labelId: Rep[Int] = column[Int]("label_id")
  def validationOptionId: Rep[Int] = column[Int]("validation_option_id")

  override def * = (galleryValidationId, labelId, validationOptionId) <>
    ((GalleryValidation.apply _).tupled, GalleryValidation.unapply)
}

/****************************************************************************
 * DB Requests
 ***************************************************************************/
object GalleryValidationQuery
    extends TableQuery(new GalleryValidationTable(_)) {
  val validations = TableQuery[GalleryValidationTable]

  /****************************************************************************
   * DB Writes
   ***************************************************************************/

  /**
   * Inserts a single validation
   * @param validation
   */
  def insertValidation(validation: GalleryValidation): Unit = {
    val insertQuery = (validations returning validations
        .map(_.galleryValidationId)) += validation
    DatabaseConfig.db.run(insertQuery)
  }
}