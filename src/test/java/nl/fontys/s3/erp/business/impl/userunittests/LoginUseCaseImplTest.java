package nl.fontys.s3.erp.business.impl.userunittests;

import nl.fontys.s3.erp.business.dtos.userdto.LoginRequest;
import nl.fontys.s3.erp.business.dtos.userdto.LoginResponse;
import nl.fontys.s3.erp.business.exceptions.EmployeeDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.InvalidCredentials;
import nl.fontys.s3.erp.business.impl.userimpl.LoginUseCaseImpl;
import nl.fontys.s3.erp.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.erp.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.entity.DepartmentEntity;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import nl.fontys.s3.erp.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @Test
    void login_throwsInvalidCredentials_whenUserNotFound() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        LoginRequest request = LoginRequest.builder()
                .email("nonexistent@example.com")
                .password("password")
                .build();

        // Act & Assert
        assertThrows(InvalidCredentials.class, () -> loginUseCase.login(request));

        // Verify: No further interactions
        verify(userRepository).findByEmail("nonexistent@example.com");
        verifyNoMoreInteractions(passwordEncoder, accessTokenEncoder);
    }
    @Test
    void login_throwsInvalidCredentials_whenPasswordDoesNotMatch() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("wrongPassword")
                .build();

        // Act & Assert
        assertThrows(InvalidCredentials.class, () -> loginUseCase.login(request));

        // Verify: Password check is performed
        verify(passwordEncoder).matches("wrongPassword", "encodedPassword");
        verifyNoMoreInteractions(accessTokenEncoder);
    }
    @Test
    void login_throwsEmployeeDoesNotExist_whenEmployeeIsNull() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .employee(null)
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        // Act & Assert
        assertThrows(EmployeeDoesNotExist.class, () -> loginUseCase.login(request));

        // Verify: Employee check is performed
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("password", "encodedPassword");
    }
    @Test
    void login_returnsAccessToken_whenCredentialsAreValid() {
        // Arrange
        DepartmentEntity accountingDepartment = DepartmentEntity.builder()
                .id(1L)
                .name("ACCOUNTING")
                .build();

        EmployeeEntity employee = EmployeeEntity.builder()
                .id(1L)
                .departments(Set.of(accountingDepartment))
                .build();

        UserEntity user = UserEntity.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .role(UserRoleEntity.builder().role(Role.MANAGER).build())
                .employee(employee)
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        String mockAccessToken = "mockAccessToken";
        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn(mockAccessToken);

        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        // Act
        LoginResponse response = loginUseCase.login(request);

        // Assert
        assertNotNull(response);
        assertEquals(mockAccessToken, response.getAccessToken());

        // Verify: Access token is generated
        verify(accessTokenEncoder).encode(any(AccessTokenImpl.class));
    }
}
