package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.domain.users.User;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import nl.fontys.s3.erp.persistence.entity.UserRoleEntity;

public class UserConverter {
    private UserConverter(){

    }
    public static User convert(UserEntity userEntity){
        return User.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole().getRole())
                .employee(EmployeeConverter.toEmployee(userEntity.getEmployee()))
                .build();
    }

    public static UserEntity convert(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(UserRoleEntity.builder().build())
                .employee(EmployeeConverter.toEmployeeEntity(user.getEmployee()))
                .build();
    }
}
