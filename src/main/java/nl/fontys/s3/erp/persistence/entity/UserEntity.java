package nl.fontys.s3.erp.persistence.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.domain.users.Status;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Length(min = 3, max = 80)
    @Column(name = "address")
    private String address;

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Length(min = 3)
    @Column(name = "password")
    private String password;

    @NotBlank
    @Length(min = 3, max = 12)
    @Column(name = "phone")
    private String phone;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Column(name = "salary")
    private BigDecimal salary;

}
