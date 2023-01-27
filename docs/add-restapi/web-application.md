# Create Web Application

see [spring.io](https://spring.io/guides/gs/spring-boot/)

see [Bootstrap a Simple Application](https://www.baeldung.com/spring-boot-start)

see [Configure a Spring Boot Web Application](https://www.baeldung.com/spring-boot-application-configuration)

## Import Runtime Dependencies

Edit `build.gradle`, eg.

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springframework.boot:spring-boot-starter-web'

    implementation 'org.springdoc:springdoc-openapi-ui:1.6.14'
    implementation 'io.micrometer:micrometer-registry-prometheus:1.10.3'
}

springBoot {
    buildInfo() // populate actuator/info endpoint
}
```

## Create Spring-Boot Application

Replace `ConsoleApplication` with [WebApplication](source/main/java/sw/jpa/foodmart/WebApplication.java)

## Add Runtime Configuration

Edit `application.properties`, eg.

```
spring.application.name=Foodmart Webservices

#
# io.springdoc setup
#

openapi.title=Foodmart Webservices
openapi.description=RestAPI for the Foodmart Webservices
openapi.version=0.1

#
# actuator endpoints ; see https://www.baeldung.com/spring-boot-actuators
#

management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.show-details=always

#
# HTTP GET parameter typing
#

spring.mvc.format.date=iso
spring.mvc.format.date-time=iso

#
# JSON serialization
#

spring.jackson.date-format=yyyy-MM-dd'T'HH:mm:ss.SSSXXX
spring.jackson.time-zone=Europe/London
```

## Verify Application

Use actuator endpoints, eg.

* [info](http://localhost:8080/actuator/info), eg.

  ```
  {
    "build": {
      "artifact": "sw-jpa-foodmart",
      "name": "sw-jpa-foodmart",
      "time": "2023-01-27T15:15:02.430Z",
      "version": "0.0.1-SNAPSHOT",
      "group": "sw.learning"
    }
  }
  ```
  
* [prometheus](http://localhost:8080/actuator/prometheus), eg.

  ```
  # HELP http_server_requests_seconds_max Duration of HTTP server request handling
  # TYPE http_server_requests_seconds_max gauge
  http_server_requests_seconds_max{exception="ResourceNotFoundException",method="GET",outcome="CLIENT_ERROR",status="404",uri="/departments/{id}",} 0.0041414
  http_server_requests_seconds_max{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/departments/{id}",} 0.015377549
  http_server_requests_seconds_max{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/departments",} 0.0
  ```
