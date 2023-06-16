package customActionBuilders

import models.UserSession
import play.api.Logging
import play.api.mvc.Results.{BadRequest, Forbidden}
import play.api.mvc._
import service.SessionService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class IdentityAction @Inject()(implicit ec: ExecutionContext,
                               sessionService: SessionService
                              ) extends ActionFilter[Request] with Logging{
  override protected def filter[A](request: Request[A]): Future[Option[Result]] = {
    var userId = new String;
    var authCookie = new String;
    request.headers.get("userId") match {
      case Some(value) => userId = value
      case None => Future.successful(Some(BadRequest))
    }
    logger.info("userId extracted from request "+userId)
    request.headers.get("Authentication") match {
      case Some(value) => authCookie = value
      case None => Future.successful(Some(BadRequest))
    }
    logger.info("AUTH TOKEN extracted from request "+authCookie)

    sessionService.validateSession(UserSession(userId,authCookie,"")) match {
      case Some(_) => Future.successful(Some(Forbidden))
      case None => Future.successful(None)
    }

  }

  override protected def executionContext: ExecutionContext = ec
}
