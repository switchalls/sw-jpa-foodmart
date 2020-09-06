create table department (department_id integer generated by default as identity, department_description varchar(255), primary key (department_id))
create table employee (employee_id integer generated by default as identity, department_id integer, education_level varchar(255), first_name varchar(255), full_name varchar(255), last_name varchar(255), position_id integer, supervisor_id integer, primary key (employee_id))
create table position (position_id integer generated by default as identity, management_role varchar(255), max_scale integer, min_scale integer, pay_type varchar(255), position_title varchar(255), primary key (position_id))
alter table employee add constraint FKbejtwvg9bxus2mffsm3swj3u9 foreign key (department_id) references department
alter table employee add constraint FKbc8rdko9o9n1ri9bpdyxv3x7i foreign key (position_id) references position
alter table employee add constraint FKc30m2tyq9as0ht5xctaypfdqp foreign key (supervisor_id) references employee