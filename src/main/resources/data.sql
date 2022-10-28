INSERT INTO department (department_id, department_description)
VALUES (10, 'Store Management');

INSERT INTO position (position_id, position_title, pay_type, min_scale, max_scale, management_role)
VALUES (20, 'Manger', 'Monthly', 0, 1000, 'HQ General Management');

INSERT INTO position (position_id, position_title, pay_type, min_scale, max_scale, management_role)
VALUES (21, 'Worker', 'Weekly', 0, 995, 'Slave');

INSERT INTO employee (employee_id, department_id, education_level, full_name, first_name, last_name, position_id)
VALUES (30, 10, 'Graduate Degree', 'Ms Bossy', 'Hard Nosed', 'Bossy', 20);

INSERT INTO employee (employee_id, department_id, education_level, full_name, first_name, last_name, position_id)
VALUES (31, 10, 'Diploma', 'Mr Slacker', 'Lazy', 'Slacker', 21);

