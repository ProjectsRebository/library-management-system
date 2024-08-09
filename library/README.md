   library system to add books and patrons and allow patrons to borrow and return books 
   
# App information 
 1- I added the needed apis 

 2- I used h2 DB in order to make app simple 
 but we can use any other DB and can download them as docker images if we don't want to set up
 DB 

 3- I used for cache spring cache along with caffeine library 
 and the data will be saved for 5 minutes and then invalidated also when update or delete done
 data will be also invalidated. we can also use redis but as data is simple then I preferred to make caching 
 simple using spring boot caching

 4- post man collection with sample data attached so that the tester can use it to test 
 I also added pagination for get all books and get patrons to enhance performance 

 5- for authentication, I used basic auth because only for simplicity (user nme : admin, password: password) 

### to run application you can run local or if you dont want to run it then uncomment docker file maven plugin in pom file then build the app it will create docker image for you then run this image using below command it will create docker image 
###  "docker run -p 8080:8080 library-management:0.0.1-SNAPSHOT"
