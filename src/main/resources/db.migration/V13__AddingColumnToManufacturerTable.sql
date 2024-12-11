ALTER TABLE manufacturer
    ADD COLUMN status VARCHAR(40) NOT NULL DEFAULT 'ACTIVE';

UPDATE manufacturer
SET status = 'ACTIVE';

ALTER TABLE manufacturer
    ALTER COLUMN status DROP DEFAULT;