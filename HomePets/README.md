# HOME PETS

## Use cases

* A person can have an animal
* Animals are dogs and cats
* If person disappeared, his animals disappeared too
* Animal couldn't have owner
* Owners can swap animals

## Requirements

* Spring Boot web service 
* CRUD operations for entities
* Validation 
* Spring profiles

## Database

### Jpa layer
![](docs/img/jpa_scheme.png)

### Database layer
![](docs/img/database_scheme.png)

## Postman

You can test API with using postman [collection](docs/postman)

## SAP Cloud Foundry 
To deploy application 
* Register account on [hana.ondemand.com](https://account.hana.ondemand.com/#/home/welcome)
* Create trial home account 
* Download cf cli from [here](https://docs.cloudfoundry.org/cf-cli/install-go-cli.html)
* Set _API endpoint_ : 
    * Cross over Subaccount and get link
    * Put ```cf api [your endpoint]``` API ENDPOINT [help](https://docs.cloudfoundry.org/running/cf-api-endpoint.html)
* Login with your credentials ```cf login```
* Clone this repository
* From root folder (HomePats) Put commands:
    > mvn clean install spring-boot:repackage -P Cloud
  
    > cf push
  
Application successfully deployed 

You can use 'cf logs homepats --recent' to get logs from your app


In order to use JDK 11, you need to use java buildpack v4.16+.
The jre version should be set in `JBP_CONFIG_OPEN_JDK_JRE`.

`manifest.yml` will look like

```
applications:
  - name: homepats
    instance: 1
    memory: 1G
    disk_quota: 512M
    path: target/home_pats_app-1.0-SNAPSHOT.jar
    buildpack: https://github.com/cloudfoundry/java-buildpack.git#v4.16
    env:
      JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 11.+}}'
```