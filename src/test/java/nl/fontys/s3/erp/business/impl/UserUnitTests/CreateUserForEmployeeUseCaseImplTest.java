package nl.fontys.s3.erp.business.impl.UserUnitTests;

import nl.fontys.s3.erp.business.dtos.userdto.CreateUserForEmployeeRequest;
import nl.fontys.s3.erp.business.dtos.userdto.CreateUserForEmployeeResponse;
import nl.fontys.s3.erp.business.exceptions.EmployeeDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.UserAccountAlreadyExistsForEmployee;
import nl.fontys.s3.erp.business.impl.userimpl.CreateUserForEmployeeUseCaseImpl;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.persistence.EmployeeRepository;
import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.UserRoleRepository;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import nl.fontys.s3.erp.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserForEmployeeUseCaseImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserForEmployeeUseCaseImpl createUserForEmployeeUseCase;

    @Test
    void createUserForEmployee_throwsEmployeeDoesNotExist_whenEmployeeCodeNotFound() {
        // Arrange
        when(employeeRepository.findByEmployeeCode("EMP001")).thenReturn(Optional.empty());

        CreateUserForEmployeeRequest request = CreateUserForEmployeeRequest.builder()
                .employeeCode("EMP001")
                .build();

        // Act & Assert
        assertThrows(EmployeeDoesNotExist.class, () -> createUserForEmployeeUseCase.createUserForEmployee(request));

        // Verify: Employee lookup is performed
        verify(employeeRepository).findByEmployeeCode("EMP001");
        verifyNoInteractions(userRepository, userRoleRepository, passwordEncoder);
    }
    @Test
    void createUserForEmployee_throwsUserAccountAlreadyExistsForEmployee_whenUserAlreadyExists() {
        // Arrange
        EmployeeEntity employee = EmployeeEntity.builder()
                .employeeCode("EMP001")
                .build();

        when(employeeRepository.findByEmployeeCode("EMP001")).thenReturn(Optional.of(employee));
        when(userRepository.findByEmployeeEntity(employee)).thenReturn(Optional.of(new UserEntity()));

        CreateUserForEmployeeRequest request = CreateUserForEmployeeRequest.builder()
                .employeeCode("EMP001")
                .build();

        // Act & Assert
        assertThrows(UserAccountAlreadyExistsForEmployee.class, () -> createUserForEmployeeUseCase.createUserForEmployee(request));

        // Verify: User lookup is performed
        verify(userRepository).findByEmployeeEntity(employee);
        verifyNoMoreInteractions(userRepository, userRoleRepository, passwordEncoder);
    }
    @Test
    void createUserForEmployee_createsUserSuccessfully_whenValidRequest() {
        // Arrange
        EmployeeEntity employee = EmployeeEntity.builder()
                .id(1L)
                .employeeCode("EMP001")
                .build();

        UserRoleEntity roleEntity = UserRoleEntity.builder()
                .role(Role.MANAGER)
                .build();

        when(employeeRepository.findByEmployeeCode("EMP001")).thenReturn(Optional.of(employee));
        when(userRepository.findByEmployeeEntity(employee)).thenReturn(Optional.empty());
        when(userRoleRepository.findRoleByName(Role.MANAGER)).thenReturn(Optional.of(roleEntity));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        UserEntity savedUser = UserEntity.builder()
                .id(1L)
                .email("test@example.com")
                .employee(employee)
                .role(roleEntity)
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        CreateUserForEmployeeRequest request = CreateUserForEmployeeRequest.builder()
                .employeeCode("EMP001")
                .email("test@example.com")
                .password("password")
                .role(Role.MANAGER)
                .build();

        // Act
        CreateUserForEmployeeResponse response = createUserForEmployeeUseCase.createUserForEmployee(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());

        // Verify: User and role repositories are called
        verify(employeeRepository).findByEmployeeCode("EMP001");
        verify(userRepository).findByEmployeeEntity(employee);
        verify(userRoleRepository).findRoleByName(Role.MANAGER);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(UserEntity.class));
    }
}
