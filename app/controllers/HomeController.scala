package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models._
import play.api.Logger
import service.{SessionService, UserService}
import customActionBuilders._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               val userService: UserService,
                              val loggingAction : LoggingAction,
                              val identityAction: IdentityAction,
                              val sessionService: SessionService) extends BaseController {

  private val logger: Logger = Logger(this.getClass())

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

  def loginUser = loggingAction { request =>
    logger.info("loginUser method invoked")
    val loginRequest = request.body.asJson.get.as[UserLogin]
    userService.login(loginRequest)
    Ok(Json.obj("status" -> "success", "message" -> "user logged in successfully!"))
      .withCookies(Cookie("authentication", sessionService.activateSession(loginRequest.userId)))
      .bakeCookies()
  }

  def getExercise(exerciseSchedule: ExerciseSchedule) = Action.andThen(identityAction) { request : Request[AnyContent] =>
    logger.info("GetExercise method invoked")
    Schedule.values.find(_.toString == exerciseSchedule.weekday.toUpperCase()) match {
      case Some(workday) => Ok(Json.obj("status" -> "success", "message" -> workday.quote))
      case None => BadRequest(Json.obj("status" -> "failed", "message" -> "Unable to find any value for param"))
    }
  }
}
