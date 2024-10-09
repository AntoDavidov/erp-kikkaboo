package nl.fontys.s3.erp.domain.products;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BabyStrollers extends Product {
    private double maxWeightCapacity;
    private int ageLimit; //in months
    private TypeOfStroller typeOfStroller;
    private boolean foldable;

}
