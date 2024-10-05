package nl.fontys.s3.erp.business.DTOs.UserDTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.s3.erp.domain.users.User;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetUsersResponse {
    private List<User> users;
}
