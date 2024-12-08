package nl.fontys.s3.erp.business.manufacturerusecases;

import nl.fontys.s3.erp.business.dtos.manufacturerdto.CreateManufacturerRequest;
import nl.fontys.s3.erp.business.dtos.manufacturerdto.CreateManufacturerResponse;

public interface CreateManufacturerUseCase {
    CreateManufacturerResponse createManufacturer(CreateManufacturerRequest request);

}
