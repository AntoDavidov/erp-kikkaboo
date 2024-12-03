//package nl.fontys.s3.erp.business.impl.UserImpl;
//
//
//import lombok.AllArgsConstructor;
//import nl.fontys.s3.erp.business.DTOs.UserDTOs.CreateUserRequest;
//import nl.fontys.s3.erp.business.DTOs.UserDTOs.CreateUserResponse;
//import nl.fontys.s3.erp.business.UserUseCases.CreateUserUseCase;
//import nl.fontys.s3.erp.domain.users.User;
//import nl.fontys.s3.erp.persistence.UserRepository;
//import nl.fontys.s3.erp.persistence.entity.UserEntity;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class CreateUserUseCaseImpl implements CreateUserUseCase {
//    private final UserRepository userRepository;
//
//    @Override
//    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
//        UserEntity userEntity = saveNewUser(createUserRequest);
//
//        return CreateUserResponse.builder()
//                .userId(userEntity.getId())
//                .build();
//    }
//
//    private UserEntity saveNewUser(CreateUserRequest request) {
//        UserEntity newUser = UserEntity.builder()
//                .email(request.getEmail())
//                .password(request.getPassword())
//                .roles(request.getRole())
//                .build();
//
//        return userRepository.save(newUser);
//    }
//}
