package nl.fontys.s3.erp.domain.users;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private Status status;
    private Set<Department> departments;
    private BigDecimal salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(employeeCode, employee.employeeCode) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(dateOfBirth, employee.dateOfBirth) &&
                Objects.equals(address, employee.address) &&
                Objects.equals(phone, employee.phone) &&
                status == employee.status &&
                Objects.equals(departments, employee.departments) &&
                Objects.equals(salary, employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeCode, firstName, lastName, dateOfBirth, address, phone, status, departments, salary);
    }
}
