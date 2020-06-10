package controllers

import javax.inject._
import play.api.mvc.{AbstractController, ControllerComponents}
import models.TestDAO
import models.labels.Label
import play.api.libs.json.Json

import scala.concurrent.{Await}
import scala.concurrent.duration.Duration

@Singleton
class TestController @Inject()(testDao: TestDAO,
                                cc: ControllerComponents
                              ) extends AbstractController(cc) {

  def appSummary = Action {
    Ok(Json.obj("message" -> "scala"))
  }

  def getTest = Action {
    // Creates ~20 client connections?
    val labels : Seq[Label] = Await.result(testDao.getSinglePanoId(), Duration(10, "seconds"))
    println(labels)
    Ok(Json.obj("ok" -> "ok"))
  }
}
