package models

import play.api.libs.json.{JsPath, JsValue, Json, Reads, Writes}

case class ExceptionResponse(errorCode: String, errorMessage: String)

object ExceptionResponse {

  implicit val implicitErrorWrites = new Writes[ExceptionResponse] {
    def writes(error: ExceptionResponse): JsValue = {
      Json.obj("errorCode" -> error.errorCode,
        "errorMessage" -> error.errorMessage,
      )
    }
  }
}