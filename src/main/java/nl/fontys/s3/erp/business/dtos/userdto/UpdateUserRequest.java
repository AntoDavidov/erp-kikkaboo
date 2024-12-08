package nl.fontys.s3.erp.business.dtos.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.s3.erp.domain.users.Role;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateUserRequest {
    private long userId;
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String address;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    @NotNull
    private Role role;
}
