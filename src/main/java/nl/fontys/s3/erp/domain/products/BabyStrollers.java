package nl.fontys.s3.erp.domain.products;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BabyStrollers extends HardwareProduct{
    private long maxWeightCapacity;
    private long ageLimit;
}
