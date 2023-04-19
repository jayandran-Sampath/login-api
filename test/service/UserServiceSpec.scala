package service

import org.scalatestplus.play.PlaySpec
import models.{Register, User, UserLogin, Login}
import org.mockito.Mockito.{doNothing, times, verify, when}
import org.scalatestplus.mockito.MockitoSugar
import repository.UserRepository

class UserServiceSpec extends PlaySpec with MockitoSugar {

  "UserService" should {

    "get List of Users from repository when there is no users" in {
      val repositoryMock = mock[UserRepository]
      val authenticationService = mock[AuthenticationService]
      val service = new UserService(repositoryMock, authenticationService)
      when(repositoryMock.getUsers).thenReturn(List.empty)
      service.getUsers() mustBe List.empty
    }

    "get List of Users from repository when there are list of users" in {
      val repositoryMock = mock[UserRepository]
      val authenticationService = mock[AuthenticationService]
      val service = new UserService(repositoryMock,authenticationService)
      val user = User("jayuser1@gmail.com", "test", "user")
      when(repositoryMock.getUsers).thenReturn(List(user))
      service.getUsers() mustBe List(user)
    }

    "registerUser - add user to repository when valid user details are passed" in {
      val repositoryMock = mock[UserRepository]
      val authenticationService = mock[AuthenticationService]
      val service = new UserService(repositoryMock,authenticationService)
      val registerDetails = Register("jayuser1@gmail.com","password" ,"test", "user")

      when(authenticationService.generateHash("password")).thenReturn("hashedvalue")
      doNothing().when(repositoryMock).registerUser(registerDetails,"hashedvalue")

      service.registerUser(registerDetails)

      verify(authenticationService,times(1)).generateHash("password")
      verify(repositoryMock,times(1)).registerUser(registerDetails,"hashedvalue")
    }

    "login - user should be able to login when valid details are passed" in {
      val repositoryMock = mock[UserRepository]
      val authenticationService = mock[AuthenticationService]
      val service = new UserService(repositoryMock,authenticationService)
      val loginDetails = UserLogin("jayuser1@gmail.com","password")

      when(repositoryMock.getUserLogin("jayuser1@gmail.com")).thenReturn(Option(Login("jayuser1@gmail.com","hashpassword")))
      doNothing().when(authenticationService).validateUserLogin("hashpassword","password")

      service.login(loginDetails)

      verify(repositoryMock,times(1)).getUserLogin("jayuser1@gmail.com")
      verify(authenticationService,times(1)).validateUserLogin("hashpassword","password")
    }

  }

}
