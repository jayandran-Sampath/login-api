package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import models.{Register, User}
import org.mockito.Mockito.{doNothing, when}
import org.scalatestplus.mockito.MockitoSugar.mock
import play.api.libs.json._
import service.UserService

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val serviceMock = mock[UserService]
      val controller = new HomeController(stubControllerComponents(),serviceMock)
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }

    "getUsers - get list of users when service returns list of users" in {
      val serviceMock = mock[UserService]
      when(serviceMock.getUsers()).thenReturn(List(User("jayuser1@gmail.com","jay","user"),User("jayuser2@gmail.com","vai","user")))
      val controller = new HomeController(stubControllerComponents(),serviceMock)
      val home = controller.getUsers().apply(FakeRequest(GET, "/users"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsJson(home) mustBe Json.toJson(List(User("jayuser1@gmail.com","jay","user"),User("jayuser2@gmail.com","vai","user")))
    }

    "render the index page from the application" in {
      val controller = inject[HomeController]
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }

    "get list of users from the router" in {
      val request = FakeRequest(GET, "/users")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsJson(home) mustBe Json.toJson(Array.empty[User])
    }

    "registerUser - register user when valid data is passed" in {
      val serviceMock = mock[UserService]
      doNothing().when(serviceMock).registerUser(Register("jayuser1@gmail.com","test1234","jay", "user"))
      val controller = new HomeController(stubControllerComponents(), serviceMock)
      val home = controller.registerUser().apply(FakeRequest(POST, "/user/register")
      .withJsonBody(Json.obj(
        "emailId" -> "jayuser1@gmail.com",
        "password" -> "test1234",
        "firstName" -> "jay",
        "lastName" -> "user")))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsJson(home) mustBe Json.obj("status" -> "success", "message" -> "user registered successfully!")
    }
  }
}
