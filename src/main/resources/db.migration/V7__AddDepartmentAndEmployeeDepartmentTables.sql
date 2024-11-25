CREATE TABLE department (
                            department_id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE employee_department (
                                     employee_id INT NOT NULL,
                                     department_id INT NOT NULL,
                                     PRIMARY KEY (employee_id, department_id),
                                     FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE,
                                     FOREIGN KEY (department_id) REFERENCES department(department_id) ON DELETE CASCADE
);

INSERT INTO department (name) VALUES
                                  ('ACCOUNTING'),
                                  ('PRODUCT'),
                                  ('TRADE'),
                                  ('E_COMMERCE');