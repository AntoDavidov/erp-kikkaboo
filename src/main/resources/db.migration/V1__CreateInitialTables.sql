-- V1__CreateInitialTables.sql

CREATE TABLE manufacturer
(
    id INT NOT NULL AUTO_INCREMENT,
    company_name VARCHAR(50) NOT NULL,
    country VARCHAR(30),
    city VARCHAR(50),
    PRIMARY KEY (id),
    UNIQUE (company_name)
);

CREATE TABLE user
(
    user_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(80),
    email VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(12),
    date_of_birth DATE,
    status VARCHAR(20),
    department VARCHAR(40),
    role VARCHAR(15),
    salary DECIMAL(10, 2),
    PRIMARY KEY (user_id),
    UNIQUE (email)
);

CREATE TABLE product
(
    product_id INT NOT NULL AUTO_INCREMENT,
    sku VARCHAR(8) NOT NULL,
    name VARCHAR(80) NOT NULL,
    short_name VARCHAR(20),
    description TEXT,
    cost_price DECIMAL(10, 2) NOT NULL,
    recommended_retail_price DECIMAL(10, 2),
    wholesale_price DECIMAL(10, 2),
    weight DECIMAL(10, 2),
    manufacturer_id INT,
    image_url VARCHAR(255),
    PRIMARY KEY (product_id),
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturer(id) ON DELETE SET NULL,
    UNIQUE (sku)
);

CREATE TABLE announcement
(
    announcement_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    content VARCHAR(255),
    created_at DATE,
    created_by_user INT,
    department VARCHAR(40),
    expiration_date DATE,
    announcement_type VARCHAR(50),
    is_department_only BIT,
    PRIMARY KEY (announcement_id),
    FOREIGN KEY (created_by_user) REFERENCES user(user_id) ON DELETE SET NULL
);

CREATE TABLE baby_stroller
(
    stroller_id INT NOT NULL,
    max_weight_capacity DECIMAL(10, 2),
    age_limit INT,
    type_of_stroller VARCHAR(50),
    foldable BIT,
    PRIMARY KEY (stroller_id),
    FOREIGN KEY (stroller_id) REFERENCES product(product_id) ON DELETE CASCADE
);
