# RestaurantsAPI

##### Used  Technologies
For creating this Backend assignment I used Spring Boot. 

##### How to get the project up and running
The easiest way to run this project is execute it in IntelliJ IDEA. To execute this project Java version 15 is required. My application is working on port 8080. 
Request example:
http://localhost:8080/discovery?lat=60.1709&lon=24.965 

##### Documentation location
Documentation for that project is generated from JavaDoc and is saved in the documentation folder  (index.html file). 


##### Unit tests
Unit Tests are located in src/test/java/com/example/RestaurantsAPI in the RestaurantsControllerTest file. <br>
Tests are created to control correctness of api requests answers. 

##### A brief description of the solution
In my solution ReadJsonFile class is responsible for reading data from a Json file. 
RestaurantsController is responsible for handling get queries. 
Restaurant is a class that is designed to keep the data in the same format as in Json.
ChosenRestaurants and Sections classes are designed to store the data in the format required to return JSON.


