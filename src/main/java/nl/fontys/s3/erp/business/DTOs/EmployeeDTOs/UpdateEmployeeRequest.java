package nl.fontys.s3.erp.business.DTOs.EmployeeDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.domain.users.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateEmployeeRequest {
    @NotBlank
    private String employeeCode;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Status status;

    @NotNull
    private Department department;

    @NotNull
    private BigDecimal salary;
}
