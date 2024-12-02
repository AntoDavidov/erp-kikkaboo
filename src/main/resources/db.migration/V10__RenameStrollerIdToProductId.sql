ALTER TABLE baby_stroller
    CHANGE COLUMN stroller_id product_id INT NOT NULL;

-- Ensure the foreign key constraint is updated
ALTER TABLE baby_stroller
DROP FOREIGN KEY baby_stroller_ibfk_1;

ALTER TABLE baby_stroller
    ADD CONSTRAINT fk_baby_stroller_product
        FOREIGN KEY (product_id)
            REFERENCES product (product_id)
            ON DELETE CASCADE;
