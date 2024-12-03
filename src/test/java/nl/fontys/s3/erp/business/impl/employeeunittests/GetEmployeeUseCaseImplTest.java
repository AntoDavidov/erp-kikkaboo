package nl.fontys.s3.erp.business.impl.employeeunittests;

import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.employeeimpl.GetEmployeeUseCaseImpl;
import nl.fontys.s3.erp.business.impl.converters.EmployeeConverter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.users.Employee;
import nl.fontys.s3.erp.domain.users.Status;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetEmployeeUseCaseImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private GetEmployeeUseCaseImpl getEmployeeUseCase;

    @Test
    void getEmployee_throwsPermissionDenied_whenDepartmentsNotAuthorized() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("HR"));

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> getEmployeeUseCase.getEmployee(1L));

        // Verify: No repository interactions
        verifyNoInteractions(employeeRepository);
    }
    @Test
    void getEmployee_returnsEmptyOptional_whenEmployeeNotFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("ACCOUNTING"));
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Employee> result = getEmployeeUseCase.getEmployee(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Verify: Interaction with repository
        verify(employeeRepository).findById(1L);
    }
    @Test
    void getEmployee_returnsEmployee_whenEmployeeFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("ACCOUNTING"));

        DepartmentEntity accountingDepartment = DepartmentEntity.builder()
                .id(1L)
                .name("ACCOUNTING")
                .build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .employeeCode("EMP001")
                .departments(Set.of(accountingDepartment))
                .address("Raya 12")
                .phone("3598963381")
                .status(Status.ACTIVE)
                .salary(BigDecimal.valueOf(2500))
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeEntity));

        Employee expectedEmployee = EmployeeConverter.toEmployee(employeeEntity);

        // Act
        Optional<Employee> result = getEmployeeUseCase.getEmployee(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(expectedEmployee, result.get());

        // Verify: Interaction with repository
        verify(employeeRepository).findById(1L);
    }
}
