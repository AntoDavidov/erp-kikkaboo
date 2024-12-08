CREATE TABLE user_role (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           role_name VARCHAR(50) NOT NULL,
                           user_id INT,
                           PRIMARY KEY (id),
                           CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);
