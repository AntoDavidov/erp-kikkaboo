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
public class CreateBabyStrollerRequest extends CreateProductRequest{

    @NotNull
    private double maxWeightCapacity;

    @NotNull
    private int ageLimit;

    @NotBlank
    private String typeOfStroller;

    @Builder.Default
    private boolean foldable = false;

}
