package nl.fontys.s3.erp.business.DTOs.ProductDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBabyStrollerRequest extends UpdateProductRequest{

    @NotNull
    private double maxWeightCapacity;

    @NotNull
    private int ageLimit;

    @NotNull
    private String typeOfStroller;

    @Builder.Default
    private boolean foldable = false;
}
