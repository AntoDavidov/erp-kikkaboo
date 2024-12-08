package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.domain.users.User;
import nl.fontys.s3.erp.persistence.entity.UserEntity;

public class UserConverter {
    private UserConverter(){

    }
    public static User convert(UserEntity userEntity){
        return User.builder()
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .build();
    }

    public static UserEntity convert(User user) {
        return UserEntity.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
