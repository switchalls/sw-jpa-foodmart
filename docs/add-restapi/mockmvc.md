# Spring Testing with MockMVC

see [introduction](https://www.baeldung.com/integration-testing-in-spring)

## Install Lombok

Use [lombok](https://projectlombok.org/) to easily create POJOs

Edit `build.gradle`, eg.

```
dependencies {
    compileOnly 'org.projectlombok:lombok:1.18.24'
    annotationProcessor 'org.projectlombok:lombok:1.18.24'
}
```

## Add Tests

Add rest-api models:

* [Employee](./source/main/java/sw/jpa/foodmart/restapi/models/Employee.java)
* [EmployeesContainer](./source/main/java/sw/jpa/foodmart/restapi/models/EmployeesContainer.java)
* [EmployeesPage](./source/main/java/sw/jpa/foodmart/restapi/models/EmployeesPage.java)
* [PageInformation](./source/main/java/sw/jpa/foodmart/restapi/models/PageInformation.java)

Add [EmployeeEntityControllerTest](./source/test/java/sw/jpa/foodmart/controllers/EmployeeEntityControllerTest.java)