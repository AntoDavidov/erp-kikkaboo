package nl.fontys.s3.erp.persistence.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.products.Country;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manufacturer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ManufacturerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @NotNull
    @Column(name = "country", nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @NotBlank
    @Length(max = 50)
    @Column(name = "city", nullable = false)
    private String city;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductEntity> products = new ArrayList<>();
}
