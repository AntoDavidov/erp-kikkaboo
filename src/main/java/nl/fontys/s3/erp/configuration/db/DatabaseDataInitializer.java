package nl.fontys.s3.erp.configuration.db;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {
    private ManufacturerRepository manufacturerRepository;
    private ProductRepository productRepository;

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

            ManufacturerEntity manufacturer5 = ManufacturerEntity.builder()
                    .companyName("Baby Star")
                    .country(Country.CHINA)
                    .city("Shanghai")
                    .build();

            ManufacturerEntity manufacturer6 = ManufacturerEntity.builder()
                    .companyName("Weibang Structure")
                    .country(Country.CHINA)
                    .city("Shenzhen")
                    .build();

            ManufacturerEntity manufacturer7 = ManufacturerEntity.builder()
                    .companyName("Kika Group ltd.")
                    .country(Country.BULGARIA)
                    .city("Sofia")
                    .build();

            ManufacturerEntity manufacturer8 = ManufacturerEntity.builder()
                    .companyName("Hus ltd.")
                    .country(Country.BULGARIA)
                    .city("Varna")
                    .build();

            ManufacturerEntity manufacturer9 = ManufacturerEntity.builder()
                    .companyName("Arh City Stroy ltd.")
                    .country(Country.BULGARIA)
                    .city("Plovdiv")
                    .build();

            ManufacturerEntity manufacturer10 = ManufacturerEntity.builder()
                    .companyName("Hippo Dreams")
                    .country(Country.CHINA)
                    .city("Guangzhou")
                    .build();

            ManufacturerEntity manufacturer11 = ManufacturerEntity.builder()
                    .companyName("ADSA")
                    .country(Country.CHINA)
                    .city("Hong Kong")
                    .build();

            // Save all manufacturers
            manufacturerRepository.save(manufacturer1);
            manufacturerRepository.save(manufacturer2);
            manufacturerRepository.save(manufacturer3);
            manufacturerRepository.save(manufacturer4);
            manufacturerRepository.save(manufacturer5);
            manufacturerRepository.save(manufacturer6);
            manufacturerRepository.save(manufacturer7);
            manufacturerRepository.save(manufacturer8);
            manufacturerRepository.save(manufacturer9);
            manufacturerRepository.save(manufacturer10);
            manufacturerRepository.save(manufacturer11);

            // Add two BabyStrollers
            addBabyStrollers(manufacturer1, manufacturer2);
        }
    }

    private void addBabyStrollers(ManufacturerEntity manufacturer1, ManufacturerEntity manufacturer2) {
//        // Baby Stroller 1
//        BabyStrollersEntity babyStroller1 = BabyStrollersEntity.builder()
//                .sku("ST12345")
//                .name("Comfort Cruiser Stroller")
//                .shortName("Cruiser")
//                .description("A comfortable stroller with excellent shock absorption.")
//                .costPrice(150.00)
//                .recommendedRetailPrice(299.99)
//                .wholeSalePrice(200.00)
//                .manufacturer(manufacturer1)
//                .weight(9.5)
//                .imageUrl("https://example.com/images/stroller1.jpg")
//                .maxWeightCapacity(20.0)
//                .ageLimit(36)
//                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
//                .foldable(true)
//                .build();
//
//        // Baby Stroller 2
//        BabyStrollersEntity babyStroller2 = BabyStrollersEntity.builder()
//                .sku("ST98765")
//                .name("Swift Fold Compact Stroller")
//                .shortName("Swift")
//                .description("A lightweight, compact stroller that's easy to fold.")
//                .costPrice(120.00)
//                .recommendedRetailPrice(249.99)
//                .wholeSalePrice(180.00)
//                .manufacturer(manufacturer2)
//                .weight(7.8)
//                .imageUrl("https://example.com/images/stroller2.jpg")
//                .maxWeightCapacity(18.0)
//                .ageLimit(30)
//                .typeOfStroller(TypeOfStroller.TWINS)
//                .foldable(true)
//                .build();
//
//
//        productRepository.save(babyStroller1);
//        productRepository.save(babyStroller2);
    }
}
