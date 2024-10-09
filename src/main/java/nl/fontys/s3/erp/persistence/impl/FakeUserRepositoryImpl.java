package nl.fontys.s3.erp.persistence.impl;

import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class FakeUserRepositoryImpl implements UserRepository {
    private static long NEXT_ID = 1;
    private final List<UserEntity> savedUsers;

    public FakeUserRepositoryImpl() { this.savedUsers = new ArrayList<UserEntity>(); }


    @Override
    public UserEntity findById(long id) {
        return this.savedUsers.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void deleteById(long id) {
        this.savedUsers.removeIf(user -> user.getId() == id);
    }

    @Override
    public boolean existsById(long id) {
        return this.savedUsers.stream().anyMatch(user -> user.getId() == id);
    }

    @Override
    public int count() {
        return this.savedUsers.size();
    }

    @Override
    public List<UserEntity> findAll() {
        return Collections.unmodifiableList(this.savedUsers);
    }


    @Override
    public UserEntity save(UserEntity user) {
        if(user.getId() == null) {
            user.setId(NEXT_ID);
            NEXT_ID++;
            this.savedUsers.add(user);
        }else {
            UserEntity existingUser = this.findById(user.getId());
            if(existingUser != null) {
                existingUser.setPassword(user.getPassword());
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmail(user.getEmail());
                existingUser.setPhone(user.getPhone());
                existingUser.setAddress(user.getAddress());
                existingUser.setDateOfBirth(user.getDateOfBirth());
                existingUser.setSalary(user.getSalary());
                existingUser.setRole(user.getRole());
                existingUser.setDepartment(user.getDepartment());
                existingUser.setSalary(user.getSalary());
            }else {
                this.savedUsers.add(user);
            }

        }
        return user;
    }


}
