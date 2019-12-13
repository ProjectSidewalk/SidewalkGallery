package controllers

import javax.inject._
import models.labels.{Label, LabelQuery}
import play.api.libs.json.{JsArray, JsObject}
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * Handles backend request from the GalleryOverview component.
 * @param cc
 */
class GalleryOverviewController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

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
    // Hard-coded labelTypeId, should become a parameter.
    val labelTypeId: Int = 1
    val labels: Seq[Label] = Await.result(LabelQuery.getLabels(labelTypeId, count),
      Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(LabelQuery.toLabelMetadata)
    Future.successful (
      Ok(JsArray(labelJson))
    )
  }
}
