package nl.fontys.s3.erp.business.dtos.employeedto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateEmployeeRequest {
    private Long employeeId;

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
    private Status status;

    @NotNull
    private Set<Department> department;

    @NotNull
    private BigDecimal salary;
}
