package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(int id);

    UserEntity findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.employee.id = :employeeId")
    Optional<UserEntity> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT u FROM UserEntity u WHERE u.employee = :employeeEntity")
    Optional<UserEntity> findByEmployeeEntity(EmployeeEntity employeeEntity);

}
