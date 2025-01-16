package nl.fontys.s3.erp.business.impl.userunittests;

import nl.fontys.s3.erp.business.dtos.userdto.UpdateUserRequest;
import nl.fontys.s3.erp.business.impl.userimpl.UpdateUserUseCaseImpl;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import nl.fontys.s3.erp.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void updateUser_successfulUpdate() {
        // Arrange
        UserRoleEntity roleEntity = UserRoleEntity.builder()
                .role(Role.CEO)
                .build();

        // Mock EmployeeEntity
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(8L) // Matches accessToken.getEmployeeId()
                .employeeCode("EMP001")
                .build();

        // Mock UserEntity
        UserEntity existingUserEntity = UserEntity.builder()
                .id(8L)
                .email("old.email@example.com")
                .password("hashedPassword123")
                .role(roleEntity)
                .employee(employeeEntity)
                .build();

        // Create UpdateUserRequest
        UpdateUserRequest request = new UpdateUserRequest(
                8L, // User ID
                "new.email@example.com",
                "currentPassword123",
                "newPassword123"
        );

        // Mock behaviors
        when(accessToken.getEmployeeId()).thenReturn(8L);
        when(userRepository.findByEmployeeId(8L)).thenReturn(Optional.of(existingUserEntity));
        when(userRepository.findById(8L)).thenReturn(Optional.of(existingUserEntity));
        when(passwordEncoder.matches("currentPassword123", "hashedPassword123")).thenReturn(true);
        when(passwordEncoder.encode("newPassword123")).thenReturn("newHashedPassword123");

        // Act
        updateUserUseCase.updateUser(request);

        // Assert
        verify(userRepository).save(argThat(user ->
                user.getEmail().equals("new.email@example.com") &&
                        user.getPassword().equals("newHashedPassword123")
        ));
    }

    @Test
    void updateUser_invalidOldPassword_throwsException() {
        // Arrange
        UserRoleEntity roleEntity = UserRoleEntity.builder()
                .role(Role.CEO)
                .build();

        UserEntity existingUserEntity = UserEntity.builder()
                .id(8L)
                .email("old.email@example.com")
                .password("hashedPassword123")
                .role(roleEntity)
                .employee(EmployeeEntity.builder()
                        .id(1L)
                        .employeeCode("EMP001")
                        .build())
                .build();

        UpdateUserRequest request = new UpdateUserRequest(8L, "new.email@example.com", "wrongPassword123", "newPassword123");

        when(accessToken.getEmployeeId()).thenReturn(1L); // Employee ID matches
        when(userRepository.findByEmployeeId(1L)).thenReturn(Optional.of(existingUserEntity)); // Fetch by employeeId
        when(passwordEncoder.matches("wrongPassword123", "hashedPassword123")).thenReturn(false); // Old password fails

        // Act & Assert
        assertThrows(SecurityException.class, () -> updateUserUseCase.updateUser(request));
        verify(userRepository, never()).save(any()); // Ensure nothing is saved
    }

    @Test
    void updateUser_userNotFoundByEmployeeId_throwsException() {
        // Arrange
        UpdateUserRequest request = new UpdateUserRequest(8L, "new.email@example.com", "currentPassword123", "newPassword123");

        when(accessToken.getEmployeeId()).thenReturn(1L); // Employee ID
        when(userRepository.findByEmployeeId(1L)).thenReturn(Optional.empty()); // No user found

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> updateUserUseCase.updateUser(request));
        verify(userRepository, never()).save(any()); // Ensure nothing is saved
    }

    @Test
    void updateUser_unauthorizedAccess_throwsException() {
        // Arrange
        UpdateUserRequest request = new UpdateUserRequest(8L, "new.email@example.com", "currentPassword123", "newPassword123");

        when(accessToken.getEmployeeId()).thenReturn(9L); // Employee ID mismatch

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> updateUserUseCase.updateUser(request));
        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
    }
}
