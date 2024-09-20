package nl.fontys.s3.erp.business.ManufacturerUseCases;

import nl.fontys.s3.erp.domain.products.Manufacturer;

public interface GetManufacturerUseCase {
    Manufacturer getManufacturer(long id);


}
