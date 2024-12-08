package nl.fontys.s3.erp.business.impl.employeeunittests;

import nl.fontys.s3.erp.business.dtos.employeedto.GetAllEmployeesResponse;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.employeeimpl.GetAllEmployeesUseCaseImpl;
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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllEmployeesUseCaseImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private GetAllEmployeesUseCaseImpl getAllEmployeesUseCase;

    @Test
    void getAllEmployees_throwsPermissionDenied_whenDepartmentsNotAuthorized() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("HR"));

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> getAllEmployeesUseCase.getAllEmployees());

        // Verify: No repository interactions
        verifyNoInteractions(employeeRepository);
    }
    @Test
    void getAllEmployees_returnsEmptyList_whenNoEmployeesFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("ACCOUNTING"));
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        GetAllEmployeesResponse response = getAllEmployeesUseCase.getAllEmployees();

        // Assert
        assertNotNull(response);
        assertTrue(response.getEmployees().isEmpty());

        // Verify: Interaction with repository
        verify(employeeRepository).findAll();
    }
    @Test
    void getAllEmployees_returnsListOfEmployees_whenEmployeesExist() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("ACCOUNTING"));

        DepartmentEntity accountingDepartment = DepartmentEntity.builder()
                .id(1L)
                .name("ACCOUNTING")
                .build();

        DepartmentEntity marketingDepartment = DepartmentEntity.builder()
                .id(2L)
                .name("E_COMMERCE")
                .build();

        EmployeeEntity employeeEntity1 = EmployeeEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .employeeCode("EMP001")
                .departments(Set.of(accountingDepartment))
                .address("Raya 12")
                .phone("3598963381")
                .status(Status.ACTIVE)
                .salary(BigDecimal.valueOf(2500))
                .build();

        EmployeeEntity employeeEntity2 = EmployeeEntity.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .employeeCode("EMP002")
                .departments(Set.of(marketingDepartment))
                .address("Raya 12")
                .phone("3598963381")
                .status(Status.ACTIVE)
                .salary(BigDecimal.valueOf(2500))
                .build();

        List<EmployeeEntity> employeeEntities = List.of(employeeEntity1, employeeEntity2);
        when(employeeRepository.findAll()).thenReturn(employeeEntities);

        Employee employee1 = EmployeeConverter.toEmployee(employeeEntity1);
        Employee employee2 = EmployeeConverter.toEmployee(employeeEntity2);

        // Act
        GetAllEmployeesResponse response = getAllEmployeesUseCase.getAllEmployees();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getEmployees().size());
        assertEquals(employee1, response.getEmployees().get(0));
        assertEquals(employee2, response.getEmployees().get(1));

        // Verify: Interaction with repository
        verify(employeeRepository).findAll();
    }

}
