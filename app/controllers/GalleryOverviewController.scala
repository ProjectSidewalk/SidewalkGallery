package controllers

import javax.inject._
import models.labels.{Label, LabelQuery}
import play.api.libs.json.{JsArray, JsObject, Json}
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class GalleryOverviewController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def getCurbRampLabels(count: Int) = Action.async { implicit request =>
    val labelTypeId: Int = 1
    val labels: Seq[Label] = Await.result(LabelQuery.getLabels(labelTypeId, 10), Duration(10, "seconds"))
    val labelJson: Seq[JsObject] = labels.map(toLabelMetadata)
    Future.successful (
      // Ok(testing(labelTypeId, count))
      Ok(JsArray(labelJson))
    )
  }

  def toLabelMetadata(label: Label): JsObject = {
    Json.obj("labelId: " -> label.labelId)
  }

  def testing(labelTypeId: Int, count: Int): JsObject = {
    Json.obj("labelTypeId" -> labelTypeId,
              "count" -> count)
  }

}
