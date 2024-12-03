package nl.fontys.s3.erp.business.dtos.employeedto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.erp.domain.users.Department;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

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

    @Pattern(regexp = "\\+?\\d{7,15}", message = "Phone number must be valid")
    @NotBlank
    private String phone;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Set<Department> departments;

    @NotNull
    private BigDecimal salary;
}
