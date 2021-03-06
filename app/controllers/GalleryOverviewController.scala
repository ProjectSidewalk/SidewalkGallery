package controllers

import javax.inject._
import models.labels.{Label, LabelQuery}
import models.tags.{Tag, TagQuery}
import play.api.libs.json.{JsArray, JsError, JsObject, Json}
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import formats.GalleryValidationFormats.GalleryValidationReads
import models.validations.{GalleryValidation, GalleryValidationQuery}

/**
 * Handles backend requests from the GalleryOverview component.
 * @param cc
 */
@Singleton
class GalleryOverviewController @Inject()(cc: ControllerComponents)
      extends AbstractController(cc) {

  // Label Type IDs for each of the label types.
  final val curbRamp: Int = 1
  final val missingCurbRamp: Int = 2
  final val obstacle: Int = 3
  final val surfaceProblem: Int = 4
  final val noSidewalk: Int = 7

  /**
   * Returns curb ramp labels.
   * @param count Number of curb ramp labels to return.
   * @return
   */
  def getCurbRampLabels(count: Int): Action[AnyContent] = Action.async {
    // TODO (@aileenzeng): Eventually rename this to just return labels, and
    //  modify the label query appropriately.
    val labels: Seq[Label] = Await.result(LabelQuery.getLabels(curbRamp, count),
      Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelJson)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }

  def getMissingCurbRampLabels(count: Int): Action[AnyContent] = Action.async {
    val labels: Seq[Label] = Await.result(LabelQuery
      .getLabels(missingCurbRamp, count), Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelJson)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }

  def getObstacleLabels(count: Int): Action[AnyContent] = Action.async {
    val labels: Seq[Label] = Await.result(LabelQuery.getLabels(obstacle, count),
      Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelJson)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }

  def getSurfaceProblemLabels(count: Int): Action[AnyContent] = Action.async {
    val labels: Seq[Label] = Await.result(LabelQuery
      .getLabels(surfaceProblem, count), Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelJson)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }

  def getNoSidewalkLabels(count: Int): Action[AnyContent] = Action.async {
    val labels: Seq[Label] = Await.result(LabelQuery
      .getLabels(noSidewalk, count), Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelJson)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }

  def getTags(labelTypeId: Int): Action[AnyContent] = Action.async {
    val tags: Seq[Tag] = Await.result(TagQuery
      .getTags(labelTypeId), Duration(10, "seconds"))
    val tagsJson: Seq[JsObject] = tags.map(TagQuery.toTagMetadata)
    Future.successful(
      Ok(JsArray(tagsJson))
    )
  }

  def submitValidation(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      request.body.asJson.foreach { json =>
        // Gets the request parameters and validates the inputs.
        val submission = json.validate(GalleryValidationReads)
        submission.fold(
          errors => {
            Future.successful(BadRequest(Json.obj(
              "success" -> false, "message" -> JsError.toJson(errors))))
          },
          submission => {
            GalleryValidationQuery.insertValidation(GalleryValidation(0,
              submission.labelId, submission.validationResult))
            Future.successful(Ok(Json.obj("success" -> true)))
          }
        )
      }

      Future.successful(
        Ok(JsArray())
      )
  }
}
