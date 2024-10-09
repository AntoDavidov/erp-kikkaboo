package nl.fontys.s3.erp.business.ManufacturerUseCases;

import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;

public interface ManufacturerIdValidator {
    void validateManufacturerId(Long manufacturerId) throws ManufacturerDoesNotExist;
}
