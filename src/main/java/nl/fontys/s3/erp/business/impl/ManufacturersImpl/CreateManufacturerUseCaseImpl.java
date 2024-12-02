package nl.fontys.s3.erp.business.impl.ManufacturersImpl;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ManufacturerUseCases.CreateManufacturerUseCase;
import nl.fontys.s3.erp.business.exceptions.ManufacturerAlreadyExists;
import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.CreateManufacturerRequest;
import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.CreateManufacturerResponse;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CreateManufacturerUseCaseImpl implements CreateManufacturerUseCase {
    private final ManufacturerRepository manufacturerRepository;
    private final AccessToken accessToken;


    @Override
    public CreateManufacturerResponse createManufacturer(CreateManufacturerRequest request) {
        if(accessToken.getDepartments() == null || !accessToken.getDepartments().contains("TRADE")) {
            throw new PermissionDenied("add a manufacturer.");
        }
        if(manufacturerRepository.existsByCompanyNameCustom(request.getCompanyName())) {
            throw new ManufacturerAlreadyExists();
        }
        ManufacturerEntity savedManufacturers = saveNewManufacturer(request);

        return CreateManufacturerResponse.builder()
                .manufacturerId(savedManufacturers.getId())
                .build();
    }

    private ManufacturerEntity saveNewManufacturer(CreateManufacturerRequest request) {
        ManufacturerEntity newManufacturer = ManufacturerEntity.builder()
                .companyName(request.getCompanyName())
                .city(request.getCity())
                .country(request.getCountry())
                .build();

        return manufacturerRepository.save(newManufacturer);
    }
}
