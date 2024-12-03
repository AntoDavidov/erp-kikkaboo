package nl.fontys.s3.erp.domain.users;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String password;
    private Role role;
    private Employee employee;
}
