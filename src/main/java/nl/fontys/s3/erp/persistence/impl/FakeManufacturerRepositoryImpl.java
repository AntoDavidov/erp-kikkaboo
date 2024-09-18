package nl.fontys.s3.erp.persistence.impl;

import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FakeManufacturerRepositoryImpl implements ManufacturerRepository {
    private static long NEXT_ID = 1;
    private final List<ManufacturerEntity> savedManufacturers;

    public FakeManufacturerRepositoryImpl() {
        this.savedManufacturers = new ArrayList<ManufacturerEntity>();
    }

    @Override
    public ManufacturerEntity save(ManufacturerEntity manufacturer) {
        if(manufacturer.getId() == null){
            manufacturer.setId(NEXT_ID++);
            NEXT_ID++;
            this.savedManufacturers.add(manufacturer);

        }
        return manufacturer;
    }


}
