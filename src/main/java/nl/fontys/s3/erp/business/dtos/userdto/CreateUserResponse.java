package nl.fontys.s3.erp.business.dtos.userdto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserResponse {
    private Long userId;
}
