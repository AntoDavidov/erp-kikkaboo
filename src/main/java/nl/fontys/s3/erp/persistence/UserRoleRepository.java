package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.persistence.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
    @Query("SELECT r FROM UserRoleEntity r WHERE r.role = :role")
    Optional<UserRoleEntity> findRoleByName(@Param("role") Role role);
}
