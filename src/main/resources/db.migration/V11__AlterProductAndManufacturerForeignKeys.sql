ALTER TABLE product DROP FOREIGN KEY product_ibfk_1;

ALTER TABLE product
    ADD CONSTRAINT product_ibfk_1
        FOREIGN KEY (manufacturer_id)
            REFERENCES manufacturer (id)
            ON DELETE SET NULL;


INSERT INTO manufacturer (id, company_name, country, city)
SELECT -1, 'Unknown Manufacturer', 'UNKNOWN', 'Unknown City'
    WHERE NOT EXISTS (
    SELECT 1 FROM manufacturer WHERE id = -1
);