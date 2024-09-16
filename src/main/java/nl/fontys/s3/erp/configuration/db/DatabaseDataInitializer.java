package nl.fontys.s3.erp.configuration.db;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import nl.fontys.s3.erp.persistence.ProductRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
//@AllArgsConstructor
//public class DatabaseDataInitializer {
//    private ProductRepository productRepository;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void populateDatabaseInitialDummyData() {
//        if (productRepository)
//    }
//}
