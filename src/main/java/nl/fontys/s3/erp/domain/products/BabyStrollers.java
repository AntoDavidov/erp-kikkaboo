package nl.fontys.s3.erp.domain.products;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BabyStrollers extends HardwareProduct{
    private long maxWeightCapacity;
    private long ageLimit;
}
