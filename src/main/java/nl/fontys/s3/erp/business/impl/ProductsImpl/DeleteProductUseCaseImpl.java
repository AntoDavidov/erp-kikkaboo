package nl.fontys.s3.erp.business.impl.ProductsImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ProductsUseCases.DeleteProductUseCase;
import nl.fontys.s3.erp.persistence.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductRepository productRepository;

    @Override
    public void deleteProduct(long id){
        productRepository.deleteById(id);
    }
}
