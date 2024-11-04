package nl.fontys.s3.erp.domain.users;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String password;
    private String phone;
    private Date dateOfBirth;
    private Status status;
    private Department department;
    private Role role;
    private BigDecimal salary;
}
