# HOME PETS Client 

## General 
Client use destination service to make queries to main service.

Provide 3 request:

- get all patients `uri: /destination/persons`
- get all dogs `uri: /destination/dogs`
- get all cats `uri: /destination/cats`

#### Application Manifest

```
applications:
- name: homepetsclient
  instance: 1
  memory: 1G
  disk_quota: 512M
  path: target/HomePetsClient-1.0-SNAPSHOT.jar
  buildpack: https://github.com/cloudfoundry/java-buildpack.git#v4.16
  env:
  JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 11.+}}'
  services:
    - petsDestination
```

#### Destination configuration 


![](/docs/img/dest_conf.png)


## Useful links
- Using destination service to connect to [other services](https://sap.github.io/cloud-sdk/docs/java/features/connectivity/sdk-connectivity-destination-service)