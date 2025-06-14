package nl.fontys.s3.erp.business.impl.ProductsImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ProductsUseCases.CreateProductUseCase;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.CreateBabyStrollerRequest;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.CreateProductRequest;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.CreateProductResponse;
import nl.fontys.s3.erp.business.ManufacturerUseCases.ManufacturerIdValidator;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.ProductExistsBySKU;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerIdValidator manufacturerIdValidator;

    @Override
    public CreateProductResponse createProduct(CreateProductRequest request) {
        // Check if the request is for creating a BabyStroller
        if (request instanceof CreateBabyStrollerRequest) {
            if (productRepository.existsBySku(request.getSku())) {
                throw new ProductExistsBySKU();
            }

            manufacturerIdValidator.validateManufacturerId(request.getManufacturerId());

            ManufacturerEntity manufacturer = manufacturerRepository.findById(request.getManufacturerId())
                    .orElseThrow(ManufacturerDoesNotExist::new);


            CreateBabyStrollerRequest babyStrollerRequest = (CreateBabyStrollerRequest) request;

            BabyStrollersEntity babyStrollerEntity = BabyStrollersEntity.builder()
                    .sku(babyStrollerRequest.getSku())
                    .name(babyStrollerRequest.getName())
                    .shortName(babyStrollerRequest.getShortName())
                    .description(babyStrollerRequest.getDescription())
                    .costPrice(babyStrollerRequest.getCostPrice())
                    .recommendedRetailPrice(babyStrollerRequest.getRecommendedRetailPrice())
                    .wholeSalePrice(babyStrollerRequest.getWholeSalePrice())
                    .manufacturer(manufacturer)
                    .weight(babyStrollerRequest.getWeight())
                    .imageUrl(babyStrollerRequest.getImageURL())
                    .maxWeightCapacity(babyStrollerRequest.getMaxWeightCapacity())
                    .ageLimit(babyStrollerRequest.getAgeLimit())
                    .typeOfStroller(babyStrollerRequest.getTypeOfStroller())
                    .foldable(babyStrollerRequest.isFoldable())
                    .build();

            babyStrollerEntity.calculatePrices();
            babyStrollerEntity.classifyWeight();

            BabyStrollersEntity savedProduct = (BabyStrollersEntity) productRepository.save(babyStrollerEntity);

            return CreateProductResponse.builder()
                    .productId(savedProduct.getProductId())
                    .build();
        }

        throw new IllegalArgumentException("Unsupported product type");
    }

}
