package nl.fontys.s3.erp.domain.products;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {

    private Long id;
    private String companyName;
    private Country country;
    private String city;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Manufacturer that = (Manufacturer) o;

        if (!id.equals(that.id)) return false;
        if (!companyName.equals(that.companyName)) return false;
        if (!city.equals(that.city)) return false;
        return country.equals(that.country);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + companyName.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }

}
