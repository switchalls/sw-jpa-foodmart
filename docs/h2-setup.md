# Database Setup

## Database Schema

Test suites can create the [database schema](../src/test/resources/schema.sql) from the JPA models.

Enable the following properties in [jpa.properties](../src/test/resources/jpa.properties):

```
spring.jpa.properties.javax.persistence.schema-generation.scripts.action=create
spring.jpa.properties.javax.persistence.schema-generation.scripts.create-source=metadata
spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target=schema.sql
```

Copy `/schema.sql` into `src/test/resources`

*NB.* Spring automatically creates database when `schema.sql` present on classpath.

## Database Content

The main application can be instructed to re-create the `foodmart-db` database on startup.

Spring can be configured to create the [database schema](../src/test/resources/schema.sql) from the JPA models.

Spring can be configured to run [SQL scripts](../src/main/resources/data.sql) on startup.

Enable the following properties in [application.properties](../application.properties):

```
spring.jpa.hibernate.ddl-auto=create
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```


