package nl.fontys.s3.erp.business.dtos.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.s3.erp.domain.users.Role;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserForEmployeeRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String employeeCode;

    @NotNull
    private Role role;
}
