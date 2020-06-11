package formats

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads}

object GalleryValidationFormats {
  case class GalleryValidationFormat(labelId: Int,
                                     validationResult: Int)

  implicit val GalleryValidationReads : Reads[GalleryValidationFormat] = (
    (JsPath \ "label_id").read[Int] and
      (JsPath \ "validation_result").read[Int]
    )(GalleryValidationFormat.apply _)
}
