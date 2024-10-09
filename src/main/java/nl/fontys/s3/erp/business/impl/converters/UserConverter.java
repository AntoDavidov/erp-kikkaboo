package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.domain.users.User;
import nl.fontys.s3.erp.persistence.entity.UserEntity;

public class UserConverter {
    private UserConverter(){

    }
    public static User convert(UserEntity userEntity){
        return User.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .address(userEntity.getAddress())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .phone(userEntity.getPhone())
                .dateOfBirth(userEntity.getDateOfBirth())
                .status(userEntity.getStatus())
                .department(userEntity.getDepartment())
                .role(userEntity.getRole())
                .salary(userEntity.getSalary())
                .build();
    }
}
