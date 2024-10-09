package nl.fontys.s3.erp.business.DTOs.ProductDTOs;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductsRequest {
    private String companyName;
}
