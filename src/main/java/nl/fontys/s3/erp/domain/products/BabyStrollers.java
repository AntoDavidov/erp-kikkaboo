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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; // Compare parent fields

        BabyStrollers that = (BabyStrollers) o;

        if (Double.compare(that.maxWeightCapacity, maxWeightCapacity) != 0) return false;
        if (ageLimit != that.ageLimit) return false;
        if (foldable != that.foldable) return false;
        return typeOfStroller == that.typeOfStroller;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode(); // Include hashCode from Product
        result = 31 * result + Double.hashCode(maxWeightCapacity);
        result = 31 * result + Integer.hashCode(ageLimit);
        result = 31 * result + Boolean.hashCode(foldable);
        result = 31 * result + (typeOfStroller != null ? typeOfStroller.hashCode() : 0);
        return result;
    }

}
