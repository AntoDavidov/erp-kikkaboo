package nl.fontys.s3.erp.business.impl.userimpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.dtos.userdto.UpdateUserRequest;
import nl.fontys.s3.erp.business.userusecases.UpdateUserUseCase;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final AccessToken accessToken;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUser(UpdateUserRequest request) {
        UserEntity currentUser = userRepository.findByEmployeeId(accessToken.getEmployeeId())
                .orElseThrow(() -> new AccessDeniedException("User not found."));

        if (currentUser.getId() != request.getId()) {
            throw new AccessDeniedException("You cannot change another user's information.");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), currentUser.getPassword())) {
            throw new SecurityException("Old password is incorrect.");
        }

        UserEntity userToUpdate = userRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        userToUpdate.setEmail(request.getEmail());
        userToUpdate.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(userToUpdate);
    }
}
