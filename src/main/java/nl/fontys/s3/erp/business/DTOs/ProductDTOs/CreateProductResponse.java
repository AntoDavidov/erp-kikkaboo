package nl.fontys.s3.erp.business.DTOs.ProductDTOs;


import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductResponse {
    private Long productId;
}
