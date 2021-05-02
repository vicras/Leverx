# Dealer Stat

The goal of the project is to create a platform that provides independent ratings of in-game items _traders_. The
ratings are calculated according to the reviews provided by all the traders' customers.  
At the same time, traders list their in-game items for sale so anyone can get acquainted with them. Each review and in-game item is checked by the
platform administrator before publication.

## ROLES:

Several roles are used:

- Admin
- Trader
- Anonymous

## USE CASE:

General use cases:

1) A Trader registers on the site and lists the items for sale. The Administrator decides whether the request is valid or not.

2) An Anonymous comes to the site, looks at the offers of a certain Trader and leaves his/her feedback about the Trader.
   The Administrator decides whether the feedback may be posted or not.

3) In case Anonymous does not find the desired Trader, he/she can register trader's account and list the items for sale/leave feedback, if necessary. The administrator verifies the information and accepts or rejects the publication request.

## REGISTRATION:

To register on the platform, you must use a **valid** email address.  
After registration, a confirmation letter will be sent to it.  
Besides, the email address will be used in case of password recovery.  
The link in confirmation letters is valid for **24 hours**.

## MAIN TECHNOLOGIES:

* **REST style** with Spring Framework
* **Spring MVC** development pattern
*  **Spring security** and **JWT** for authorization and authentication.
* **MySQL** Database for used data storage. The interaction with database is implemented through the **ORM** model using **Hibernate**.
* Standard **EE Mail service** for mailing
* **Thymeleaf** for confirmation emails(Mime messages) generation
* **Redis** for fast data retrieval
* **Java 11**
* **Lombok** for Boilerplate code reducing
* **Log4j** for logging
* Integration testing and unit testing:
    - hamcrest
    - mokito
    - junit-jupiter
    - spring-test
    - spring-security-test

## BUILD:

Several artifacts are generated during the application compilation:

* webapp-runner.jar
* DealerStat-0.0.1-SNAPSHOT.war

#### WAR

The application can be used to run on your own server. For example it works perfectly on tomcat:9.0.45.

#### JAR

War application can be run without a special server installation.  
Use **command**: `java -jar webapp-runner.jar DealerStat-0.0.1-SNAPSHOT.war`

Requirements for running on local machine device:

* Redis:  _localhost:6379_
* MySql:  _localhost:3306_

## DOCKER:

WARNING!!!  
Before docker usage application artifacts must be compiled. Following command may be used:   
`mvn clean package -Dmaven.test.skip=true`

### Dockerfile

Create image tomcat:9.0.45 with DealerStat-0.0.1-SNAPSHOT.war deployed on it with  `docker build .`

### docker-compose.yml

`docker-compose up` creates 3 containers:

* mysql:5.7.34 (mysql-for-dealer-stat)
* redis:latest (redis-for-dealer-stat)
* web application (dealer-stat)

The application uses:

- Redis on: _redisdb:6379_
- Mysql on: _mysqldb:3306_

It is still impossible to use the email service for registration in docker container due to
javax.net.ssl.SSLHandshakeException Could not converting socket to TLS  
_Will be fixed in the future._

## DATABASE:

#### MySQL

**MySQL service 5.7.33 for Linux** is used in development process  
Database is represented in **Third Normal Form** (3NF)

Database scheme:

| ![](https://github.com/vicras/Leverx/blob/master/DealerStat/docs/image/dealer_stat_db_scheme.png) | 
| :--: | 
| *Scheme* |

Scheme creation script:
[link](https://github.com/vicras/Leverx/blob/master/DealerStat/docs/scripts/dealer-stat-schemas-init.sql)  
Database initialization script:
[link](https://github.com/vicras/Leverx/blob/master/DealerStat/docs/scripts/dealer-stat-db-init.sql)  

#### Redis

**Redis 4.0.9** is used to store activation codes  
Data is stored in key-value format:

* **Key**: unique UUID value
* **Value**: user id

Each code automatically expires in 24 hours

## AUTHENTICATION AND AUTHORIZATION:

**Authentication** is implemented with **Spring security**  
Sessions are removed and default authentication is changed for _Jackson Web Token_ (JWT)
Default **HS256** algorithm is used for signature    
PAYLOAD contains following information _(CLAIMS):_

    * creation date
    * expiration date. 
    * user id
    * role

Tokens expire in 1 hour.  
The user password is hashed with the **Bcrypt algorithm** for storing in the database
User should add JWT token to request header:  
Authorization: "Bearer JWT"

| ![](https://github.com/vicras/Leverx/blob/master/DealerStat/docs/image/jwt_example.png)| 
| :--: | 
| *Token example* |


## TESTING:

Integration testing was carried out for the service End points  
Unit testing was carried out for the service layer logic   
For testing purposes different database was used

| ![](https://github.com/vicras/Leverx/blob/master/DealerStat/docs/image/tests_passed.png)| 
| :--: | 
| *Test passed* |


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
| ![](https://github.com/vicras/Leverx/blob/master/DealerStat/docs/image/confirm_message.png)| 
| :--: | 
| *Confirm message example* |

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
| ![](https://github.com/vicras/Leverx/blob/master/DealerStat/docs/image/reset_message.png)| 
| :--: | 
| *Forgot password message example* |

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
Postman collection can be imported to speed up debugging and development processes

| ![](https://github.com/vicras/Leverx/blob/master/DealerStat/docs/image/postman.png)| 
| :--: | 
| *Postman example* |

Dealer stats collection supports following variables
1) First, specify _base_url_ collection variable, URI, where application is running
2) To crate a query with authentication, get JWT token from login request and specify _admin_token_ and _trader_token_ in collection variables

The collection can be downloaded from [here](https://github.com/vicras/Leverx/blob/master/DealerStat/docs/Dealer%20Stat.postman_collection.json)

Good luck!!!
