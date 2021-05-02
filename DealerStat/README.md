# Dealer Stat

The goal of the project is to create a service that provides an independent rating to _trader_ of in-game items. The
rating of the traders is based on the reviews that any user of the network can offer. At the same time, the seller
places a game item on the service, which anyone can get acquainted with. Each review and game item is checked by the
service administrator before publication.

## ROLES:

Several roles are used to work with the service:

- Admin
- Trader
- Anonymous

## USE CASE:

General user use case:

1) A trader registers on the site, adds the desired items. The administrator reviews the content and accepts or rejects
   the posting request.

2) An anonymous user comes to the site, looks at the offers of a certain trader, leaves his feedback about the trader.
   The administrator reviews the review and accepts or rejects the request to post it.

3) An anonymous user comes to the site, does not find the desired trader and registers his account, if necessary, with
   in-game items and reviews for him. The administrator verifies the information and accepts or rejects the request to
   publish it.

## REGISTRATION:

To register on the service, you must use a **valid** email address.  
After registration, a letter will be sent to the mailing address for the registration account.  
Also, the email address will be used if the user forgets the password and decides to change it.  
The link in such letters is valid for **24 hours**.

## MAIN TECHNOLOGIES:

* Application is built in **REST style** using Spring Framework
* Used the **Spring MVC** development pattern
* Authorization and authentication was carried out using **Spring security**, using **JWT** technology.
* **MySQL** Database is used to store user data, interaction with which occurs through the **ORM** model using **Hibernate**.
* Standard **EE Mail service** is used to send emails
* Emails are Mime message, the **Thymeleaf** template engine is used to generate them when the application is running
* **Redis** is used for fast data retrieval
* Used **Java 11**
* **Lombok** project was used to reduce Boilerplate code
* **Log4j** is used for logging
* For integration testing and unit testing, the following is used:
    - hamcrest
    - mokito
    - junit-jupiter
    - spring-test
    - spring-security-test

## BUILD:

The compilation of the application generates several artifacts:

* webapp-runner.jar
* DealerStat-0.0.1-SNAPSHOT.war

#### WAR

Can be used to run on your server own server. For example on tomcat:9.0.45 application works perfectly.

#### JAR

With using this artifact you can run war application without installing special server.  
Use **command**: `java -jar webapp-runner.jar DealerStat-0.0.1-SNAPSHOT.war`

To run on local machine device must have:

* Redis:  _localhost:6379_
* MySql:  _localhost:3306_

## DOCKER:

WARNING!!!  
Before use docker compile application artifacts. You can use command: `mvn clean package -Dmaven.test.skip=true`

### Dockerfile

Create image tomcat:9.0.45 and DealerStat-0.0.1-SNAPSHOT.war deployed on it. Use command `docker build .`

### docker-compose.yml

With using command `docker-compose up` you create 3 containers:

* mysql:5.7.34 (mysql-for-dealer-stat)
* redis:latest (redis-for-dealer-stat)
* web application (dealer-stat)

Application use:

- Redis on: _redisdb:6379_
- Mysql on: _mysqldb:3306_

Now, it is still impossible to use the email service in docker container for registration due to
javax.net.ssl.SSLHandshakeException Could not converting socket to TLS  
_Will be fixed in the future._

## DATABASE:

#### MySQL

During development, I used **MySQL service 5.7.33 for linux**  
Database is represented in **Third Normal Form** (3NF)

Database scheme:
| ![](https://github.com/vicras/Leverx/blob/realese/0.1.0/DealerStat/docs/image/dealer_stat_db_scheme.png) | | :--: | | *Scheme* |

Scheame creation script:
[](https://github.com/vicras/Leverx/blob/realese/0.1.0/DealerStat/docs/scripts/dealer-stat-schemas-init.sql)
Database initialization script:
[](https://github.com/vicras/Leverx/blob/realese/0.1.0/DealerStat/docs/scripts/dealer-stat-db-init.sql)

#### Redis

**Redis 4.0.9** is used to store activation codes  
Storage occurs in key-value format:

* **Key**: unique UUID value
* **Value**: user id

Each code has automatically expired after 24 hours

## AUTHENTICATION AND AUTHORIZATION:

**Authentication** is done with spring-security  
Sessions was removed and default authentication was changed for _Jackson Web Token_ (JWT)
For signature was used default **HS256** algorithm  
PAYLOAD contains information _(CLAIMS):_

    * creation date
    * expiration date. 
    * user id
    * role

Token Expire after 1 hour.  
The user password is stored in the database in a hashed form using the **Bcrypt algorithm**
User should add JWT token to request header:  
Authorization: "Bearer JWT"
| ![](https://github.com/vicras/Leverx/blob/realese/0.1.0/DealerStat/docs/image/jwt_example.png)| | :--: | | *Token example |


## TESTING:

Integration testing of the service End points  
Unit testing of the service logic layer  
For testing used another database
| ![](https://github.com/vicras/Leverx/blob/realese/0.1.0/DealerStat/docs/image/tests_passed.png)| | :--: | | *Test passed |


## END POINTS:

#### GAME:
POST request _(URL: /game)_:

    { "title": "Call of Duty" }

GET request _(URL: /game)_:

    [
        {
            "id": 4,
            "title": "Metro Exodus"
        },
        {
            "id": 7,
            "title": "Call of Duty"
        }
    ]

#### AUTHENTICATION:

Sign up _(URL: /auth/sign_up)_:
POST request

    {
        "email": "victor.graskov@gmail.com",
        "firstName" : "Viktor",
        "lastName": "Graskov",
        "password": "admin",
        "role": "ADMIN"
    }
| ![](https://github.com/vicras/Leverx/blob/realese/0.1.0/DealerStat/docs/image/confirm_message.png)| | :--: | | *Confirm message example |

Login _(/auth/login)_:
GET request

    {
        "email":"email",
        "password":"password"
    }

Confirm sign up:   
GET request: /auth/confirm/{secret_code}

Forgot password:    
POST request: /auth/forgot_password  
body: email
| ![](https://github.com/vicras/Leverx/blob/realese/0.1.0/DealerStat/docs/image/reset_message.png)| | :--: | | *Forgot password message example |

Reset password  
POST request: /auth/reset_password  
body: code=secret_code&password=newPassword

Check code validness:  
GET request: /auth/check_code
body: code

#### GAME OBJECT:

All game objects:  
GET request _(/object)_

Add game object:  
POST request _(/object)_  
Authorized **true** (TRADER)  

    {
        "title": "New game object",
        "description": "best",
        "gameKeys": [ 4, 7 ]
    }

Update existing game object  
PUT request _(/object)_  
Authorized **true** (TRADER)   

    {
        "id": "33",
        "title": "New game object (update)",
        "description": "already not  best",
        "gameKeys": [ 4, 7 ]
    }

Delete your oun object:  
DELETE request _(/object/{id})_  
Authorized **true** (TRADER)  

Get all my objects:  
GET request _(/object/my)_  
Authorized **true** (TRADER)  

Get objects for approve:  
GET request _(/object/for_approve)_  
Authorized **true**(ADMIN)  

Approve objects with ids:  
POST request _(/object/approve)_    
body:  `[ 1 , 3, 5 ]`  
Authorized **true**(ADMIN)  

Decline objects with ids:  
POST request _(/object/decline)_  
body:  `[ 1 , 3, 5 ]`  
Authorized **true**(ADMIN)  

#### COMMENT:

Get comment with id:
GET request _(/comment/{id})_

Add comment for user with id:
POST request _(/comment/user/12)_

    {
        "mark": "4",
        "message": "new comment",
        "destinationUserId": "12"
    }

Get comments about trader with id:  
You can be choice order and amounts;  
* GET request _(/comment/user/{id})_  
* GET request _(/comment/user/{id}/best?amounts=10)_  
* GET request _(/comment/user/{id}/worst?amounts=10)_  

Add new user with objects (optional) and comment
POST request _(/comment/)_

    {
        "user": {
            "email": "victor.graskov@gmail.com",
            "firstName": "Viktor",
            "lastName": "Graskov",
            "password": "admin",
            "role": "TRADER"
        },
        "objectDTOS": [
            {
                "title": "New game object",
                "description": "description",
                "gameKeys": [ 4, 7 ]
            }
        ],
        "commentDTO": {
            "mark": "4",
            "message": "new comment",
        }
    }

Get comments for approve:  
GET request _(/comment/for_approve)_  
Authorized **true**(ADMIN)  

Approve comments with ids:  
POST request _(/comment/approve)_  
body:  `[ 1 , 3, 5 ]`  
Authorized **true**(ADMIN)  

Decline comments with ids:  
POST request _(/comment/decline)_  
body:  `[ 1 , 3, 5 ]`  
Authorized **true**(ADMIN)  

## POSTMAN:
You can import postman collection to make debugging and developing faster 
| ![](https://github.com/vicras/Leverx/blob/realese/0.1.0/DealerStat/docs/image/postman.png)| | :--: | | *Postman example |
Dealer stat collection support variables
1) First, specify _base_url_ collection variable, URI, where application is running
2) To make query, which use authentication, get JWT token from login request and specify _admin_token_ and _trader_token_ in collection variables

You can download collection from [link](https://github.com/vicras/Leverx/blob/realese/0.1.0/DealerStat/docs/Dealer%20Stat.postman_collection.json)

Good luck!!!
