package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {

    @Query("select count(m) > 0 from ManufacturerEntity m where m.companyName = ?1")
    boolean existsByCompanyNameCustom(String companyName);

    @Query("SELECT m FROM ManufacturerEntity m WHERE m.id = :placeholderId")
    ManufacturerEntity getPlaceholderManufacturer(@Param("placeholderId") Long placeholderId);
}
