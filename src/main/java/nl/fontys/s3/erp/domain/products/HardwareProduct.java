package nl.fontys.s3.erp.domain.products;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HardwareProduct extends Product {
    private double weight;
    private boolean foldable;

}
