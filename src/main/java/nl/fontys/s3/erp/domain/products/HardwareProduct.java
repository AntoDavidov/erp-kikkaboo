package nl.fontys.s3.erp.domain.products;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HardwareProduct extends Product {
    private double weight;
    private boolean foldable;

}
