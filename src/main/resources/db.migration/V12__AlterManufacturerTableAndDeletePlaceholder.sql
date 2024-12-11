-- Step 1: Drop the modified foreign key constraint
ALTER TABLE product DROP FOREIGN KEY product_ibfk_1;

-- Step 2: Add back the original foreign key constraint without ON DELETE SET NULL
ALTER TABLE product
    ADD CONSTRAINT product_ibfk_1
        FOREIGN KEY (manufacturer_id)
            REFERENCES manufacturer (id)
            ON DELETE CASCADE;

-- Step 3: Remove the 'Unknown Manufacturer' record
DELETE FROM manufacturer
WHERE id = -1
  AND company_name = 'Unknown Manufacturer'
  AND country = 'UNKNOWN'
  AND city = 'Unknown City';
