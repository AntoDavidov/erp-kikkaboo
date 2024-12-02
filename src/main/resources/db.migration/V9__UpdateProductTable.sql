-- V9__UpdateProductTable.sql
ALTER TABLE product
    ADD COLUMN product_type VARCHAR(50) NOT NULL;