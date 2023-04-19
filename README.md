# login-api
Scala Play framework which handles user registration &amp; login

This sample project included with two apis exposed (/user/register, /user/login) 
and made use of bcrypt hashing. This project implemented with logging, common error handling, unit test, dependency injection, controller/service/repository/user models, json object casting.

# Run the service

Service runs in local host : 9000

````
sbt run
````

# Test the service

````
sbt test
````

# Sample curls

Register user
````
curl \
    --header "Content-type: application/json" \
    --request POST \
    --data '{"emailId":"testuser1@gmail.com", "password":"password","firstName":"testUser", "lastName":"userone"}' \
    http://localhost:9000/user/register
````

Login user
````
curl \
    --header "Content-type: application/json" \
    --request POST \
    --data '{"emailId":"testuser1@gmail.com", "password":"password"}' \
    http://localhost:9000/user/login
````
