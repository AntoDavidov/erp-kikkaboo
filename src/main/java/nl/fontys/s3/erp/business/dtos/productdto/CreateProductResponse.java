package nl.fontys.s3.erp.business.dtos.productdto;


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
