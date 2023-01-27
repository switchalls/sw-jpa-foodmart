# Spring Data Rest

> Spring Data REST ... makes it easy to build hypermedia-driven REST web services on top of Spring Data repositories

see [introduction](https://www.baeldung.com/spring-data-rest-intro)

see [spring.io](https://spring.io/projects/spring-data-rest)

## Import Runtime Dependencies

Edit `build.gradle`, eg.

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-rest'
    implementation 'org.springdoc:springdoc-openapi-data-rest:1.6.14'
}
```

## Use Swagger UI

Launch [swagger](http://localhost:8080/swagger-ui/index.html#/)

Drive API using data defined by [data.sql](../../src/main/resources/data.sql), eg. `/employees` should return:

```
{
  "_embedded": {
    "employees": [
      {
        "fullName": "Ms Bossy",
        "firstName": "Hard Nosed",
        "lastName": "Bossy",
        "educationLevel": "Graduate Degree",
        "_links": {
          "self": {
            "href": "http://localhost:8080/employees/30"
          },
          "employee": {
            "href": "http://localhost:8080/employees/30"
          },
          "position": {
            "href": "http://localhost:8080/employees/30/position"
          },
          "supervisor": {
            "href": "http://localhost:8080/employees/30/supervisor"
          },
          "department": {
            "href": "http://localhost:8080/employees/30/department"
          }
        }
      },
      {
        "fullName": "Mr Slacker",
        "firstName": "Lazy",
        "lastName": "Slacker",
        "educationLevel": "Diploma",
        "_links": {
          "self": {
            "href": "http://localhost:8080/employees/31"
          },
          "employee": {
            "href": "http://localhost:8080/employees/31"
          },
          "position": {
            "href": "http://localhost:8080/employees/31/position"
          },
          "supervisor": {
            "href": "http://localhost:8080/employees/31/supervisor"
          },
          "department": {
            "href": "http://localhost:8080/employees/31/department"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/employees?page=0&size=20"
    },
    "profile": {
      "href": "http://localhost:8080/profile/employees"
    },
    "search": {
      "href": "http://localhost:8080/employees/search"
    }
  },
  "page": {
    "size": 20,
    "totalElements": 2,
    "totalPages": 1,
    "number": 0
  }
}
```

## Fixes for Swagger UI (optional)

When running swagger, you may see:

> Fetch error
> response status is 500 /v3/api-docs

from the console:

```
2023-01-27 14:45:16.758 ERROR 21988 --- [nio-8080-exec-9] o.a.c.c.C.[.[.[/].[dispatcherServlet]    :
    Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is
        java.lang.NullPointerException: Cannot invoke "org.springframework.data.repository.query.Param.value()" because
            the return value of "java.lang.reflect.Parameter.getAnnotation(java.lang.Class)" is null]
```

To stop this error, fix [EmployeeRepository](source/main/java/sw/jpa/foodmart/models/EmployeeRepository.java) by adding `@Param` to all `findBy` methods, eg.

```
    List<Employee> findByDepartmentDescriptionAndEducationLevelAndPositionPayType(
            @Param("description") String description,
            @Param("level") String level,
            @Param("payType") String payType);
```
