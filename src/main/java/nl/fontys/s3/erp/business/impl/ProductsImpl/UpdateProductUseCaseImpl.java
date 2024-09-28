package nl.fontys.s3.erp.business.impl.ProductsImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.CreateBabyStrollerRequest;
import nl.fontys.s3.erp.business.DTOs.UpdateBabyStrollerRequest;
import nl.fontys.s3.erp.business.DTOs.UpdateProductRequest;
import nl.fontys.s3.erp.business.ProductsUseCases.UpdateProductUseCase;
import nl.fontys.s3.erp.business.exceptions.ProductDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.ProductExistsBySKU;
import nl.fontys.s3.erp.domain.products.Product;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
    private final ProductRepository productRepository;

    @Override
    public void updateProduct(UpdateProductRequest request) {
        Optional<ProductEntity> productEntityOptional = Optional.ofNullable(productRepository.findById(request.getId()));
        if (productEntityOptional.isEmpty()) {
            throw new ProductDoesNotExist();
        }

        if (productRepository.existsBySKU(request.getSku())) {
            throw new ProductExistsBySKU();
        }

        ProductEntity productEntity = productEntityOptional.get();


        updateCommonFields(request, productEntity);

        if (request instanceof UpdateBabyStrollerRequest) {
            UpdateBabyStrollerRequest babyStrollerRequest = (UpdateBabyStrollerRequest) request;
            updateBabyStrollerFields((BabyStrollersEntity) productEntity, babyStrollerRequest);
        }

        productRepository.save(productEntity);
    }

    // Method to update common fields for all products
    private void updateCommonFields(UpdateProductRequest request, ProductEntity productEntity) {
        productEntity.setSku(request.getSku());
        productEntity.setName(request.getName());
        productEntity.setShortName(request.getShortName());
        productEntity.setDescription(request.getDescription());
        productEntity.setCostPrice(request.getCostPrice());
        productEntity.setRecommendedRetailPrice(request.getRecommendedRetailPrice());
        productEntity.setWholeSalePrice(request.getWholeSalePrice());
        productEntity.setWeight(request.getWeight());
        productEntity.setImageUrl(request.getImageURL());

        productEntity.calculatePrices();
        productEntity.classifyWeight();
    }

    // Method to update specific fields for BabyStrollers
    private void updateBabyStrollerFields(BabyStrollersEntity babyStrollerEntity, UpdateBabyStrollerRequest request) {
        TypeOfStroller typeOfStroller;
        try {
            typeOfStroller = TypeOfStroller.valueOf(request.getTypeOfStroller().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid stroller type provided");
        }

        babyStrollerEntity.setMaxWeightCapacity(request.getMaxWeightCapacity());
        babyStrollerEntity.setAgeLimit(request.getAgeLimit());
        babyStrollerEntity.setTypeOfStroller(typeOfStroller);
        babyStrollerEntity.setFoldable(request.isFoldable());

    }
}
