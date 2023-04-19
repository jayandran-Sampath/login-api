package repository

import models.{Login, Register, User}
import org.scalatestplus.play.PlaySpec

class OfficeUserRepositorySpec extends PlaySpec{

  "OfficeRepository" should {

    "get List of Users from repository when there is no users" in {
      val repository = new OfficeUserRepository()
      repository.getUsers mustBe List.empty
    }

    "register User and verify it is added to the list" in {
      val repository = new OfficeUserRepository()
      repository.registerUser(Register("jayuser1@gmail.com","password","test","user"),"hashvalue")
      repository.getUsers mustBe List(User("jayuser1@gmail.com","test","user"))
    }

    "getUserLogin - get User login details when user is available" in {
      val repository = new OfficeUserRepository()
      repository.registerUser(Register("jayuser1@gmail.com","password","test","user"),"hashvalue")
      repository.getUserLogin("jayuser1@gmail.com").get mustBe Login("jayuser1@gmail.com","hashvalue")
    }

    "getUserLogin - get User login details when user is not available" in {
      val repository = new OfficeUserRepository()
      repository.getUserLogin("jayuser1@gmail.com") mustBe Option.empty[Login]
    }
  }

}
