package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
    DepartmentEntity findByName(String name);
}
