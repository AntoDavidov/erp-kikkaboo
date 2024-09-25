package nl.fontys.s3.erp.business.DTOs;


import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CreateProductResponse {
    private int productId;
}
