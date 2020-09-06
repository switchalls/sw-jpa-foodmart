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

1. Created `maven` project for `springboot` application
1. Manually analyse database

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