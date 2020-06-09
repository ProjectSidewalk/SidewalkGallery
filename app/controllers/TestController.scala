package controllers

import javax.inject._
import play.api.mvc.{ AbstractController, ControllerComponents }

import models.TestDAO
import models.demo.Label

import play.api.libs.json.Json
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

@Singleton
class TestController @Inject()(testDao: TestDAO,
                                cc: ControllerComponents
                              ) extends AbstractController(cc) {

  def appSummary = Action {
    println("[TestController.scala] hello???")
    Ok(Json.obj("message" -> "scala"))
  }

  def getTest = Action {
    println("[TestController.scala] received get request")
    // Creates ~20 client connections?
    val labels : Seq[Label] = Await.result(testDao.getSinglePanoId(), Duration(10, "seconds"))
    println(labels)
    Ok(Json.obj("ok" -> "ok"))
  }
}
