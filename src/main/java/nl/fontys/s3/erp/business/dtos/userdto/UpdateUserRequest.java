package nl.fontys.s3.erp.business.dtos.userdto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private long id;

    @NotBlank
    private String email;

    private String oldPassword;

    private String newPassword;
}
