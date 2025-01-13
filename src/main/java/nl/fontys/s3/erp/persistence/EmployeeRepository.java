package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Query("SELECT COUNT(e) > 0 FROM EmployeeEntity e WHERE e.employeeCode = :employeeCode")
    boolean existsByEmployeeCode(@Param("employeeCode") String employeeCode);

    Optional<EmployeeEntity> findByEmployeeCode(String employeeCode);

    Optional<EmployeeEntity> findById(long id);

    @Query("SELECT e.id FROM EmployeeEntity e LEFT JOIN UserEntity u ON e.id = u.employee.id WHERE u.id IS NOT NULL")
    List<Long> findEmployeeIdsWithUserAccounts();
}
