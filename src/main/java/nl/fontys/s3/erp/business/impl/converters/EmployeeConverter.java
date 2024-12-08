package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.Employee;
import nl.fontys.s3.erp.persistence.entity.DepartmentEntity;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeConverter {
    private EmployeeConverter() {

    }

    public static Employee toEmployee(EmployeeEntity entity) {
        return Employee.builder()
                .id(entity.getId())
                .employeeCode(entity.getEmployeeCode())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .dateOfBirth(entity.getDateOfBirth())
                .departments(toDepartmentEnums(entity.getDepartments()))
                .status(entity.getStatus())
                .salary(entity.getSalary())
                .build();
    }

    public static EmployeeEntity toEmployeeEntity(Employee employee) {
        return EmployeeEntity.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .address(employee.getAddress())
                .phone(employee.getPhone())
                .employeeCode(employee.getEmployeeCode())
                .dateOfBirth(employee.getDateOfBirth())
                .departments(toDepartmentEntities(employee.getDepartments()))
                .status(employee.getStatus())
                .salary(employee.getSalary())
                .build();
    }

    //Enum to Entity
    private static Set<Department> toDepartmentEnums(Set<DepartmentEntity> entities) {
        return entities.stream()
                .map(entity -> Department.valueOf(entity.getName()))
                .collect(Collectors.toSet());
    }

    //Entity to enum
    private static Set<DepartmentEntity> toDepartmentEntities(Set<Department> departments) {
        return departments.stream()
                .map(department -> DepartmentEntity.builder()
                        .name(department.name())
                        .build())
                .collect(Collectors.toSet());

    }
}
