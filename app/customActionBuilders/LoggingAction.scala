package customActionBuilders

import play.api.Logging
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class LoggingAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser)
    with Logging {
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    val requestBody = request.body.asInstanceOf[AnyContentAsJson].json
    (requestBody \ "emailId").asOpt[String].map(emailId => {
      logger.info("Login Request initiated for "+emailId)
    }).getOrElse {
      logger.error("error with login request")
    }
    block(request)
  }
}
