package nl.fontys.s3.erp.persistence.entity;


import lombok.Data;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;

@Data
@SuperBuilder
public class BabyStrollersEntity extends ProductEntity {
    private double maxWeightCapacity;

    private int ageLimit;

    private TypeOfStroller typeOfStroller;

    private boolean foldable;
}
