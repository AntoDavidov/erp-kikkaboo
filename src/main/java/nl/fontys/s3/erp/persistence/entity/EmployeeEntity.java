package nl.fontys.s3.erp.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.users.Status;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @NotBlank
    @Column(name = "employee_code", unique = true)
    private String employeeCode;

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
    @Length(min = 3, max = 12)
    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Column(name = "salary", precision = 19, scale = 2)
    private BigDecimal salary;

    @ManyToMany()
    @JoinTable(
            name = "employee_department",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<DepartmentEntity> departments;

}

