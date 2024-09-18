package nl.fontys.s3.erp.business.ManufacturerUseCases;

import nl.fontys.s3.erp.persistence.DTOs.CreateManufacturerRequest;
import nl.fontys.s3.erp.persistence.DTOs.CreateManufacturerResponse;

public interface CreateManufacturerUseCase {
    CreateManufacturerResponse createManufacturer(CreateManufacturerRequest request);

}
