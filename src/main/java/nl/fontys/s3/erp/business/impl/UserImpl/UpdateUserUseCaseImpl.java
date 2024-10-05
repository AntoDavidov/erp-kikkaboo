package nl.fontys.s3.erp.business.impl.UserImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.UpdateUserRequest;
import nl.fontys.s3.erp.business.UserUseCases.UpdateUserUseCase;
import nl.fontys.s3.erp.business.exceptions.UserDoesNotExist;
import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public void updateUser(UpdateUserRequest request) {
        Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.findById(request.getUserId()));

        if (userEntity.isEmpty()) {
            throw new UserDoesNotExist();
        }
        UserEntity user = userEntity.get();
        updateUser(request, user);
        userRepository.save(user);

    }

    private void updateUser(UpdateUserRequest request, UserEntity userEntity) {
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(request.getPassword());
        userEntity.setPhone(request.getPhone());
        userEntity.setAddress(request.getAddress());
        userEntity.setDateOfBirth(request.getDateOfBirth());
        userEntity.setStatus(request.getStatus());
        userEntity.setRole(request.getRole());
        userEntity.setDepartment(request.getDepartment());
        userEntity.setStatus(request.getStatus());

    }
}
