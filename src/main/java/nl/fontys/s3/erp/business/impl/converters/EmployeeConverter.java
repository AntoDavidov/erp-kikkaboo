package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.domain.users.Employee;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;

public class EmployeeConverter {
    private EmployeeConverter() {

    }
    public static Employee toEmployee(EmployeeEntity entity) {
        return Employee.builder()
                .employeeCode(entity.getEmployeeCode())
                .dateOfBirth(entity.getDateOfBirth())
                .department(entity.getDepartment())
                .status(entity.getStatus())
                .salary(entity.getSalary())
                .build();
    }

    public static EmployeeEntity toEmployeeEntity(Employee employee) {
        return EmployeeEntity.builder()
                .employeeCode(employee.getEmployeeCode())
                .dateOfBirth(employee.getDateOfBirth())
                .department(employee.getDepartment())
                .status(employee.getStatus())
                .salary(employee.getSalary())
                .build();
    }
}
