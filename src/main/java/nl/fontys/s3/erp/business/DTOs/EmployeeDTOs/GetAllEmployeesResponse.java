package nl.fontys.s3.erp.business.DTOs.EmployeeDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.s3.erp.domain.users.Employee;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetAllEmployeesResponse {
    private List<Employee> employees;
}
