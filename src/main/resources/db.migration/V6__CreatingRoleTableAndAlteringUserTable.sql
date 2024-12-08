CREATE TABLE roles (
                       id INT NOT NULL AUTO_INCREMENT,
                       role_name VARCHAR(50) NOT NULL UNIQUE,
                       PRIMARY KEY (id)
);

INSERT INTO roles (role_name) VALUES
                                  ('CEO'),
                                  ('MANAGER'),
                                  ('SPECIALIST');

ALTER TABLE `user`
    ADD COLUMN role_id INT NOT NULL,
    ADD CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles (id);


ALTER TABLE announcement
    ADD CONSTRAINT announcement_ibfk_1 FOREIGN KEY (created_by_user) REFERENCES `user` (user_id);
