package controllers

import javax.inject._
import models.labels.{Label, LabelQuery}
import models.tags.{Tag, TagQuery}
import play.api.libs.json.{JsArray, JsObject}
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * Handles backend request from the GalleryOverview component.
 * @param cc
 */
class GalleryOverviewController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  // Label Type IDs for each of the label types.
  val curbRamp: Int = 1
  val missingCurbRamp: Int = 2
  val obstacle: Int = 3
  val surfaceProblem: Int = 4
  val noSidewalk: Int = 7


  /**
   * TODO(@aileenzeng): Limit the number of curb ramp labels that are retrieved by this query.
   * TODO(@aileenzeng): Eventually rename this to just return labels, and modify the label query
   * appropriately.
   *
   * Returns curb ramp labels.
   * @param count Number of curb ramp labels to return.
   * @return
   */
  def getCurbRampLabels(count: Int): Action[AnyContent] = Action.async {
    println("[GalleryOverviewController]: getCurbRampLabels")
    val labels: Seq[Label] = Await.result(LabelQuery.getLabels(curbRamp, count),
      Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelMetadata)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }

  def getMissingCurbRampLabels(count: Int): Action[AnyContent] = Action.async {
    println("[GalleryOverviewController]: getCurbRampLabels")
    val labels: Seq[Label] = Await.result(LabelQuery.getLabels(missingCurbRamp, count),
      Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelMetadata)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }

  def getObstacleLabels(count: Int): Action[AnyContent] = Action.async {
    println("[GalleryOverviewController]: getCurbRampLabels")
    val labels: Seq[Label] = Await.result(LabelQuery.getLabels(obstacle, count),
      Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelMetadata)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }

  def getSurfaceProblemLabels(count: Int): Action[AnyContent] = Action.async {
    println("[GalleryOverviewController]: getCurbRampLabels")
    val labels: Seq[Label] = Await.result(LabelQuery.getLabels(surfaceProblem, count),
      Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelMetadata)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }

  def getNoSidewalkLabels(count: Int): Action[AnyContent] = Action.async {
    println("[GalleryOverviewController]: getCurbRampLabels")
    val labels: Seq[Label] = Await.result(LabelQuery.getLabels(noSidewalk, count),
      Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelMetadata)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }

  def getTags(labelTypeId: Int): Action[AnyContent] = Action.async {
    val tags: Seq[Tag] = Await.result(TagQuery.getTags(labelTypeId), Duration(10, "seconds"))
    val tagsJson: Seq[JsObject] = tags.map(TagQuery.toTagMetadata)
    Future.successful(
      Ok(JsArray(tagsJson))
    )
  }
}
