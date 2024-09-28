package nl.fontys.s3.erp.business.impl.ProductsImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ProductsUseCases.CreateProductUseCase;
import nl.fontys.s3.erp.business.DTOs.CreateBabyStrollerRequest;
import nl.fontys.s3.erp.business.DTOs.CreateProductRequest;
import nl.fontys.s3.erp.business.DTOs.CreateProductResponse;
import nl.fontys.s3.erp.business.ManufacturerUseCases.ManufacturerIdValidator;
import nl.fontys.s3.erp.business.exceptions.ProductExistsBySKU;
import nl.fontys.s3.erp.domain.products.BabyStrollers;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
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
        if (productRepository.existsBySKU(request.getSku())) {
            throw new ProductExistsBySKU();
        }

        manufacturerIdValidator.validateManufacturerId(request.getManufacturerId());

        ManufacturerEntity manufacturer = manufacturerRepository.findById(request.getManufacturerId());

        // Check if the request is for creating a BabyStroller
        if (request instanceof CreateBabyStrollerRequest) {
            CreateBabyStrollerRequest babyStrollerRequest = (CreateBabyStrollerRequest) request;

            // Convert typeOfStroller from String to Enum
            TypeOfStroller typeOfStroller;
            try {
                typeOfStroller = TypeOfStroller.valueOf(babyStrollerRequest.getTypeOfStroller().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid stroller type provided");
            }

            // Build the BabyStrollersEntity directly, no need to initialize productEntity to null first
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
                    .typeOfStroller(typeOfStroller)
                    .foldable(babyStrollerRequest.isFoldable())
                    .build();

            babyStrollerEntity.calculatePrices();
            babyStrollerEntity.classifyWeight();

            //here I cast the saved product to Baby stroller entity
            BabyStrollersEntity savedProduct = (BabyStrollersEntity) productRepository.save(babyStrollerEntity);

            // Return the response
            return CreateProductResponse.builder()
                    .productId(savedProduct.getProductId())
                    .build();
        }

        throw new IllegalArgumentException("Unsupported product type");
    }

}
