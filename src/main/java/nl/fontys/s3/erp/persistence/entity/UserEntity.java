package nl.fontys.s3.erp.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.domain.users.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class UserEntity {

    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String email;

    private String password;

    private String phone;

    private LocalDate dateOfBirth;

    private Status status;

    private Department department;

    private Role role;

    private BigDecimal salary;

}
