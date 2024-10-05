package nl.fontys.s3.erp.business.DTOs.ProductDTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBabyStrollerResponse extends CreateProductResponse {
    private int babyStrollerId;


}
