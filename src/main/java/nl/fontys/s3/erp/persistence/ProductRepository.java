package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsBySku(String sku);

    @Query("SELECT p FROM ProductEntity p WHERE p.manufacturer.companyName = :companyName")
    List<ProductEntity> findAllByManufacturerName(@Param("companyName") String companyName);

}
