-- V4__AlteringEmployeeUserRelationship.sql
--
ALTER TABLE employee
DROP FOREIGN KEY employee_ibfk_1;

ALTER TABLE employee
DROP COLUMN user_id;

ALTER TABLE user
    ADD COLUMN employee_id INT NOT NULL;

ALTER TABLE user
    ADD CONSTRAINT fk_user_employee
        FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
            ON DELETE CASCADE;

DELETE FROM user
WHERE employee_id NOT IN (SELECT employee_id FROM employee);
