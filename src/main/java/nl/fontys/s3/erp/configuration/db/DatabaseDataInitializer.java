package nl.fontys.s3.erp.configuration.db;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {
    private ManufacturerRepository manufacturerRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData() {
        if (manufacturerRepository.count() == 0) {
            // Manufacturer 1
            ManufacturerEntity manufacturer1 = ManufacturerEntity.builder()
                    .companyName("KikkaBoo Europe")
                    .country(Country.BULGARIA)
                    .city("Sofia")
                    .build();

            // Manufacturer 2
            ManufacturerEntity manufacturer2 = ManufacturerEntity.builder()
                    .companyName("Little Star Industries")
                    .country(Country.CHINA)
                    .city("Shenzhen")
                    .build();

            // Manufacturer 3
            ManufacturerEntity manufacturer3 = ManufacturerEntity.builder()
                    .companyName("Baby Joy")
                    .country(Country.INDIA)
                    .city("Mumbai")
                    .build();

            // Manufacturer 4
            ManufacturerEntity manufacturer4 = ManufacturerEntity.builder()
                    .companyName("Cozy Baby Co.")
                    .country(Country.BULGARIA)
                    .city("Plovdiv")
                    .build();

            // Save all manufacturers
            manufacturerRepository.save(manufacturer1);
            manufacturerRepository.save(manufacturer2);
            manufacturerRepository.save(manufacturer3);
            manufacturerRepository.save(manufacturer4);
        }
    }
}
