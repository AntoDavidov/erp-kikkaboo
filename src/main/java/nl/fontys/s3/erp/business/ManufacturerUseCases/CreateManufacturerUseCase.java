package nl.fontys.s3.erp.business.ManufacturerUseCases;

import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.CreateManufacturerRequest;
import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.CreateManufacturerResponse;

public interface CreateManufacturerUseCase {
    CreateManufacturerResponse createManufacturer(CreateManufacturerRequest request);

}
