package nl.fontys.s3.erp.business.dtos.productdto;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductsRequest {
    private String companyName;
}
