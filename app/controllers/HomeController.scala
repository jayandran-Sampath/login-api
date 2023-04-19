package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models._
import play.api.Logger
import service.UserService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               val userService: UserService) extends BaseController {

  private val logger: Logger = Logger(this.getClass())

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def getUsers() = Action { implicit request: Request[AnyContent] =>
    logger.info("getUsers method invoked")
    Ok(Json.toJson(userService.getUsers()))
  }

  def registerUser = Action { request =>
    logger.info("registerUser method invoked")
    val json = request.body.asJson.get
    userService.registerUser(json.as[Register])
    Ok(Json.obj("status" -> "success", "message" -> "user registered successfully!"))
  }

  def loginUser = Action { request =>
    logger.info("loginUser method invoked")
    val json = request.body.asJson.get
    userService.login(json.as[UserLogin])
    Ok(Json.obj("status" -> "success", "message" -> "user logged in successfully!"))
  }
}
