package nl.fontys.s3.erp.persistence.impl;

import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class FakeManufacturerRepositoryImpl implements ManufacturerRepository {
    private static long NEXT_ID = 1;
    private final List<ManufacturerEntity> savedManufacturers;

    public FakeManufacturerRepositoryImpl() {
        this.savedManufacturers = new ArrayList<ManufacturerEntity>();
    }


    @Override
    public ManufacturerEntity findById(long manufacturerId){
        return this.savedManufacturers.stream().filter(m -> m.getId() == manufacturerId).findFirst().orElse(null);
    }

    @Override
    public void deleteById(long manufacturerId){
        this.savedManufacturers.removeIf(m -> m.getId() == manufacturerId);
    }

    @Override
    public boolean existsByCompanyName(String companyName){
        return this.savedManufacturers.stream().anyMatch(m -> m.getCompanyName().equals(companyName));
    }

    @Override
    public boolean existsById(long manufacturerId){
        return this.savedManufacturers.stream().anyMatch(m -> m.getId() == manufacturerId);
    }

    @Override
    public int count(){
        return this.savedManufacturers.size();
    }

    @Override
    public List<ManufacturerEntity> findAll(){
        return Collections.unmodifiableList(this.savedManufacturers);
    }

    @Override
    public ManufacturerEntity save(ManufacturerEntity manufacturer) {
        if(manufacturer.getId() == null){
            manufacturer.setId(NEXT_ID);
            NEXT_ID++;
            this.savedManufacturers.add(manufacturer);

        } else {
            ManufacturerEntity existingManufacturer = this.findById(manufacturer.getId());
            if(existingManufacturer != null){
                existingManufacturer.setCountry(manufacturer.getCountry());
                existingManufacturer.setCity(manufacturer.getCity());
            } else {
                this.savedManufacturers.add(manufacturer);
            }
        }
        return manufacturer;
    }


}
