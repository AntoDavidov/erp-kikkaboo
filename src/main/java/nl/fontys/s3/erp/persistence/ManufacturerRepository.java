package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {

    @Query("select count(m) > 0 from ManufacturerEntity m where m.companyName = ?1")
    boolean existsByCompanyNameCustom(String companyName);

}
