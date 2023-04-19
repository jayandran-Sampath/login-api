package service

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import com.github.t3hnar.bcrypt._

class AuthenticationServiceSpec extends PlaySpec with GuiceOneAppPerTest {

  "AuthenticationService" should {

    "generateHash - should generate hashPassword when valid password is passed" in {
      val service = new AuthenticationService()
      val password = "password123"
      val hashPassword = service.generateHash(password)
      password.isBcryptedBounded(hashPassword) mustBe true
    }

    "generateHash - should throw exception when password is more than 71 bytes" in {
      val service = new AuthenticationService()
      val password = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ"
      val thrown = the[RuntimeException] thrownBy service.generateHash(password)
      thrown.getMessage contains "Exception occurred while parsing the password"
    }

    "validateUserLogin - should execute successfully when valid password passed with hash" in {
      val service = new AuthenticationService()
      val password = "password123"
      val hashPassword = service.generateHash(password)

      //val hashPassword = "$2a$10$xzPlLtebmYPquqQn9WWgUe3oZ3Yo71xNkTXIQH3c9KdxAgaQNeI1q"
      service.validateUserLogin(hashPassword,password)
    }

    "validateUserLogin - should throw exception when invalid password passed with hash" in {
      val service = new AuthenticationService()
      val password = "password123"
      val password1 = "password1234"
      val hashPassword = service.generateHash(password)

      val thrown = the[RuntimeException] thrownBy service.validateUserLogin(hashPassword,password1)
      thrown.getMessage contains "Invalid user"

    }
  }

}
