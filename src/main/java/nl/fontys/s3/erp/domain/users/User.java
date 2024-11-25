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
    private String email;
    private String password;
    private Role role;
    private Employee employee;
}
