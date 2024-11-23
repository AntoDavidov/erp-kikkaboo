-- V2__Split_user_and_employee.sql
CREATE TABLE employee (
                          employee_id INT NOT NULL AUTO_INCREMENT,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          address VARCHAR(80),
                          phone VARCHAR(12),
                          date_of_birth DATE,
                          status VARCHAR(20),
                          department VARCHAR(40),
                          salary DECIMAL(10, 2),
                          user_id INT UNIQUE,
                          PRIMARY KEY (employee_id),
                          FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

INSERT INTO employee (
    first_name, last_name, address, phone, date_of_birth, status, department, salary, user_id
)
SELECT
    first_name, last_name, address, phone, date_of_birth, status, department, salary, user_id
FROM user;

ALTER TABLE user
DROP COLUMN first_name,
DROP COLUMN last_name,
DROP COLUMN address,
DROP COLUMN phone,
DROP COLUMN date_of_birth,
DROP COLUMN status,
DROP COLUMN department,
DROP COLUMN salary;
