package nl.fontys.s3.erp.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.s3.erp.domain.users.Role;

@Entity
@Table(name = "roles")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private Role role;
}
