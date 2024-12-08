package nl.fontys.s3.erp.business.dtos.productdto;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBabyStrollerRequest extends CreateProductRequest{

    @NotNull
    private double maxWeightCapacity;

    @NotNull
    private int ageLimit;

    @NotNull
    private TypeOfStroller typeOfStroller;

    @Builder.Default
    private boolean foldable = false;

}
