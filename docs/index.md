# JPA Hackerthon

See [development notes](./hackerthon.md)

```
$ java -jar build/libs/sw-jpa-foodmart-0.0.1-SNAPSHOT.jar
Usage: [option] value
Options:
--pay, eg. Monthly
--education, eg. Graduate Degree
--department, eg. Store Management
```

## H2 Database Setup

The hackerthon database is no longer available and has been replaced with a local [H2](https://www.h2database.com/html/main.html) instance.

See [guide](./h2-setup.md)

## Build Changes

The hackerthon was implemented using [maven](https://maven.apache.org/) ; see [pom](./pom.xml)

Current versions of `maven` (eg. `3.6.0`) cannot be run against JDK 17

`maven` has been replaced with [gradle](https://gradle.org/)
