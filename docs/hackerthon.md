# JPA Hackerthon

## HackerRank Instructions

Write a command line Java program that allows the user to specify a department, pay-type and education level, and then connects to the shared database given below and runs a query with those options. The program should then display the results of the query.

You can access an already established MySQL database. This has three tables populated with data.

The connection details you need are:

```
spring.datasource.url=jdbc:mysql://35.187.11.81:3306/foodmart
spring.datasource.username=technical_test
spring.datasource.password=HopefullyProspectiveDevsDontBreakMe
```

## Final Thoughts

Over the years, I have come to prefer JPA to direct JDBC access. JPA provides a more OO style.

You can use spring's `JdbcTemplate` with `RowMapper`, but it feels a bit clunky for 2020 !!

Using JPA to construct the test database schema (from entities) was a mistake.

Although easy to do, I missed the need to use `BigDecimal` for `Type#DECIMAL`.

Perhaps I should have used `mysqldump`, eg.

```
$ mysqldump -u root -p --no-data dbname > schema.sql
```

Wasn't sure if all 3 options were needed in a single query?

Added example of Spring's [query by example](https://www.baeldung.com/spring-data-query-by-example) to `EmployeeRepositoryTest`, eg.

```
final Department exampleDepartment = new Department();
exampleDepartment.setDescription(SCIENCE_DEPARTMENT);

final Employee probe = new Employee();
probe.setDepartment(exampleDepartment);
probe.setEducationLevel(DEGREE_EDUCATION);

final List<Employee> result = this.testSubject.findAll(Example.of(probe, Employee.IGNORE_IDS));
```

## Solution Steps

Simple database structure. Use JPA (OR mapping) to access data.

1. Created `maven` project for `springboot` application
1. Manually analyse database (see below)
1. Create JPA entities
1. Create schema for test database (see below)
1. Create JPA test suite using `dbUnit`
1. Added basic `Employee` search methods
1. Fix JPA schema validation error (see below)
1. Added services and `main`
1. Experiment with `query by example` (see `EmployeeRepositoryTest`)
1. Cleaned up JPA annotations (see below)

### Database analysis

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

### Create schema for test database

See [database setup](./h2-setup.md)

### Fix JPA schema validation error

Error when connecting to `live` database:

```
Caused by: javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.tool.schema.spi.SchemaManagementException: Schema-validation: wrong column type encountered in column [max_scale] in table [position]; found [decimal (Types#DECIMAL)], but expecting [integer (Types#INTEGER)]
```

Use `java.math.BigDecimal` for { `min_scale`, `max_scale` }

### Cleaned up JPA relationships

Removed `Employee` properties { `department_id`, `position_id` }

Fields are auto generated by JPA when { `department`, `position` } set.

Verified by extending `EmployeeRepositoryTest` and using `@ExpectedDatabase` to verify

## Build Changes

The hackerthon was implemented using [maven](https://maven.apache.org/) ; see [pom](./pom.xml)

Current versions of `maven` (eg. `3.6.0`) cannot be run against JDK 17

`maven` has been replaced with [gradle](https://gradle.org/)