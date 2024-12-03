package nl.fontys.s3.erp.persistence.entity;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "baby_stroller")
@DiscriminatorValue("BABY_STROLLER")
@SuperBuilder
public class BabyStrollersEntity extends ProductEntity {

    @Column(name = "max_weight_capacity", nullable = false)
    private double maxWeightCapacity;

    @Column(name = "age_limit", nullable = false)
    private int ageLimit;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "type_of_stroller", nullable = false)
    private TypeOfStroller typeOfStroller;

    @Column(name = "foldable", nullable = false)
    private boolean foldable;
}
