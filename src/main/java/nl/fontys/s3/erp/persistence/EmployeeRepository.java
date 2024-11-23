package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Query("SELECT e FROM EmployeeEntity e WHERE e.user = :user")
    Optional<EmployeeEntity> findByUser(@Param("user") UserEntity user);

    @Query("SELECT COUNT(e) > 0 FROM EmployeeEntity e WHERE e.employeeCode = :employeeCode")
    boolean existsByEmployeeCode(@Param("employeeCode") String employeeCode);

    Optional<EmployeeEntity> findById(long id);
}
