package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;


public interface ManufacturerRepository {
    ManufacturerEntity save(ManufacturerEntity entity);
}
