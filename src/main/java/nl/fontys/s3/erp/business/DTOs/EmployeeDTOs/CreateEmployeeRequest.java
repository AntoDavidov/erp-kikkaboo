package nl.fontys.s3.erp.business.DTOs.EmployeeDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.domain.users.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateEmployeeRequest {

    @NotBlank
    private String employeeCode;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String address;

    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "Phone number must be valid")
    @NotBlank
    private String phone;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Status status;

    @NotNull
    private Department department;

    @NotNull
    private BigDecimal salary;
}
