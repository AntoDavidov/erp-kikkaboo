package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.UserEntity;

import java.util.List;

public interface UserRepository {
    UserEntity save(UserEntity entity);

    UserEntity findById(long id);

    void deleteById(long id);

    boolean existsById(long id);

    List<UserEntity> findAll();

    int count();
}
