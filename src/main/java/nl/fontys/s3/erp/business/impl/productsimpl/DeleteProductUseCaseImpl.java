package nl.fontys.s3.erp.business.impl.productsimpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.productsusecases.DeleteProductUseCase;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.persistence.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductRepository productRepository;
    private final AccessToken accessToken;

    @Override
    public void deleteProduct(long id){
        if(accessToken.getDepartments() == null || !accessToken.getDepartments().contains("PRODUCT")) {
            throw new PermissionDenied("delete a product.");
        }
        productRepository.deleteById(id);
    }
}
