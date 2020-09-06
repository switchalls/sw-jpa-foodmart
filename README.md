# Matilion Senior Developer Test

## Test Instructions

Write a command line Java program that allows the user to specify a department, pay-type and education level, and then connects to the shared database given below and runs a query with those options. The program should then display the results of the query.

You can access an already established MySQL database. This has three tables populated with data.

The connection details you need are:

```
spring.datasource.url=jdbc:mysql://35.187.11.81:3306/foodmart
spring.datasource.username=technical_test
spring.datasource.password=HopefullyProspectiveDevsDontBreakMe
```

## Solution Steps

Simple database structure. Use JPA (OR mapping) to access data.

1. Created `maven` project for `springboot` application
1. Manually analyse database (see below)
1. Create JPA entities based on database analysis
1. Create database schema (see below)
1. Create JPA test suite
1. Added search criteria to JPA repositories
1. Fixed schema error ; use `java.math.BigDecimal` for { `min_scale`, `max_scale` }

### Database analyse

```
$ mysql foodmart -h 35.187.11.81 -P 3306 --user=technical_test --password=HopefullyProspectiveDevsDontBreakMe
mysql> show tables;
+--------------------+
| Tables_in_foodmart |
+--------------------+
| department         |
| employee           |
| position           |
+--------------------+
3 rows in set (0.02 sec)

mysql> desc department;
+------------------------+-------------+------+-----+---------+-------+
| Field                  | Type        | Null | Key | Default | Extra |
+------------------------+-------------+------+-----+---------+-------+
| department_id          | int(11)     | NO   | PRI | NULL    |       |
| department_description | varchar(30) | NO   |     | NULL    |       |
+------------------------+-------------+------+-----+---------+-------+
2 rows in set (0.03 sec)

mysql> desc employee;
+-----------------+---------------+------+-----+---------+-------+
| Field           | Type          | Null | Key | Default | Extra |
+-----------------+---------------+------+-----+---------+-------+
| employee_id     | int(11)       | NO   | PRI | NULL    |       |
| full_name       | varchar(30)   | NO   |     | NULL    |       |
| first_name      | varchar(30)   | NO   |     | NULL    |       |
| last_name       | varchar(30)   | NO   |     | NULL    |       |
| position_id     | int(11)       | YES  |     | NULL    |       |
| position_title  | varchar(30)   | YES  |     | NULL    |       |
| store_id        | int(11)       | NO   | MUL | NULL    |       |
| department_id   | int(11)       | NO   | MUL | NULL    |       |
| birth_date      | date          | NO   |     | NULL    |       |
| hire_date       | datetime      | YES  |     | NULL    |       |
| end_date        | datetime      | YES  |     | NULL    |       |
| salary          | decimal(10,4) | NO   |     | NULL    |       |
| supervisor_id   | int(11)       | YES  | MUL | NULL    |       |
| education_level | varchar(30)   | NO   |     | NULL    |       |
| marital_status  | varchar(30)   | NO   |     | NULL    |       |
| gender          | varchar(30)   | NO   |     | NULL    |       |
| management_role | varchar(30)   | YES  |     | NULL    |       |
+-----------------+---------------+------+-----+---------+-------+
17 rows in set (0.02 sec)
 
mysql> desc position;
+-----------------+---------------+------+-----+---------+-------+
| Field           | Type          | Null | Key | Default | Extra |
+-----------------+---------------+------+-----+---------+-------+
| position_id     | int(11)       | NO   | PRI | NULL    |       |
| position_title  | varchar(30)   | NO   |     | NULL    |       |
| pay_type        | varchar(30)   | NO   |     | NULL    |       |
| min_scale       | decimal(10,4) | NO   |     | NULL    |       |
| max_scale       | decimal(10,4) | NO   |     | NULL    |       |
| management_role | varchar(30)   | NO   |     | NULL    |       |
+-----------------+---------------+------+-----+---------+-------+
6 rows in set (0.03 sec)
```

### Create database schema

Instruct spring to create a database schema (based on JPA entities) by:

Setting the following in `jpa.properties`

```
spring.jpa.properties.javax.persistence.schema-generation.scripts.action=create
spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target=schema.sql
spring.jpa.properties.javax.persistence.schema-generation.scripts.create-source=metadata
```

Run the application

Copy `/schema.sql` into `src/test/resources`

### Schema validation errors

Error when connecting to `real` database:

```
Caused by: javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.tool.schema.spi.SchemaManagementException: Schema-validation: wrong column type encountered in column [max_scale] in table [position]; found [decimal (Types#DECIMAL)], but expecting [integer (Types#INTEGER)]
```

Use `java.math.BigDecimal` for { `min_scale`, `max_scale` }

