package nl.fontys.s3.erp.business.impl.employeeunittests;

import nl.fontys.s3.erp.business.dtos.employeedto.CreateEmployeeRequest;
import nl.fontys.s3.erp.business.dtos.employeedto.CreateEmployeeResponse;
import nl.fontys.s3.erp.business.exceptions.EmployeeAlreadyExistsByCode;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.employeeimpl.CreateEmployeeUseCaseImpl;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.Status;
import nl.fontys.s3.erp.persistence.DepartmentRepository;
import nl.fontys.s3.erp.persistence.EmployeeRepository;
import nl.fontys.s3.erp.persistence.entity.DepartmentEntity;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateEmployeeUseCaseImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private CreateEmployeeUseCaseImpl createEmployeeUseCase;

    @Test
    void createEmployee_throwsPermissionDenied_whenDepartmentsNotAuthorized() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("HR"));

        CreateEmployeeRequest request = CreateEmployeeRequest.builder()
                .employeeCode("EMP001")
                .build();

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> createEmployeeUseCase.createEmployee(request));

        // Verify: No repository interactions
        verifyNoInteractions(employeeRepository, departmentRepository);
    }
    @Test
    void createEmployee_throwsEmployeeAlreadyExistsByCode_whenCodeAlreadyExists() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("ACCOUNTING"));
        when(employeeRepository.existsByEmployeeCode("EMP001")).thenReturn(true);

        CreateEmployeeRequest request = CreateEmployeeRequest.builder()
                .employeeCode("EMP001")
                .build();

        // Act & Assert
        assertThrows(EmployeeAlreadyExistsByCode.class, () -> createEmployeeUseCase.createEmployee(request));

        // Verify: No further interactions
        verify(employeeRepository).existsByEmployeeCode("EMP001");
        verifyNoMoreInteractions(employeeRepository, departmentRepository);
    }
    @Test
    void createEmployee_throwsIllegalArgumentException_whenDepartmentDoesNotExist() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("ACCOUNTING"));
        when(employeeRepository.existsByEmployeeCode("EMP001")).thenReturn(false);
        when(departmentRepository.findByName("E_COMMERCE")).thenReturn(null);

        CreateEmployeeRequest request = CreateEmployeeRequest.builder()
                .employeeCode("EMP001")
                .firstName("John")
                .lastName("Doe")
                .departments(Set.of(Department.E_COMMERCE))
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> createEmployeeUseCase.createEmployee(request));

        // Verify: Department lookup is performed
        verify(departmentRepository).findByName("E_COMMERCE");
        verifyNoMoreInteractions(employeeRepository);
    }
    @Test
    void createEmployee_savesSuccessfully_whenValidRequest() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("ACCOUNTING"));
        when(employeeRepository.existsByEmployeeCode("EMP001")).thenReturn(false);

        DepartmentEntity accountingDepartment = DepartmentEntity.builder()
                .id(1L)
                .name("ACCOUNTING")
                .build();

        when(departmentRepository.findByName("ACCOUNTING")).thenReturn(accountingDepartment);

        EmployeeEntity savedEmployee = EmployeeEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .employeeCode("EMP001")
                .departments(Set.of(accountingDepartment))
                .status(Status.ACTIVE)
                .build();

        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(savedEmployee);

        CreateEmployeeRequest request = CreateEmployeeRequest.builder()
                .employeeCode("EMP001")
                .firstName("John")
                .lastName("Doe")
                .departments(Set.of(Department.ACCOUNTING))
                .address("123 Street")
                .phone("1234567890")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .salary(BigDecimal.valueOf(5000))
                .build();

        // Act
        CreateEmployeeResponse response = createEmployeeUseCase.createEmployee(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());

        // Verify: Save is performed
        verify(employeeRepository).save(any(EmployeeEntity.class));
    }
}
