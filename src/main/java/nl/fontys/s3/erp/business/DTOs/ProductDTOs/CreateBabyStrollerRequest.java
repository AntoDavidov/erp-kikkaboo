package nl.fontys.s3.erp.business.DTOs.ProductDTOs;


import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private TypeOfStroller typeOfStroller;

    @Builder.Default
    private boolean foldable = false;

}
