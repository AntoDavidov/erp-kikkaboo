package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;

import java.util.List;
import java.util.Optional;


public interface ManufacturerRepository {
    ManufacturerEntity save(ManufacturerEntity entity);

    Optional<ManufacturerEntity> findById(long id);

    void deleteById(long id);

    boolean existsById(long id);

    boolean existsByCompanyName(String companyName);

    List<ManufacturerEntity> findAll();

    int count();


}
