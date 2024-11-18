package nl.fontys.s3.erp.persistence.entity;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("BABY_STROLLER")
@SuperBuilder
public class BabyStrollersEntity extends ProductEntity {

    @Column(name = "max_weight_capacity", nullable = false)
    private double maxWeightCapacity;

    @Column(name = "age_limit", nullable = false)
    private int ageLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_stroller", nullable = false)
    private TypeOfStroller typeOfStroller;

    @Column(name = "is_foldable", nullable = false)
    private boolean foldable;
}
