package nl.fontys.s3.erp.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.dtos.productdto.*;
import nl.fontys.s3.erp.business.productsusecases.*;
import nl.fontys.s3.erp.domain.products.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final GetProductsUseCase getProductsUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    @GetMapping
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER') or hasRole('SPECIALIST')")
    public ResponseEntity<GetAllProductsResponse> getAllProducts(@RequestParam(value = "manufacturer", required = false) String companyName) {
        GetAllProductsRequest request = GetAllProductsRequest.builder().companyName(companyName).build();
        GetAllProductsResponse response = getProductsUseCase.getAllProducts(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER') or hasRole('SPECIALIST')")
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request) {
        CreateProductResponse response = createProductUseCase.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER') or hasRole('SPECIALIST')")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") final long id) {
        final Product product = getProductUseCase.getProduct(id);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER') or hasRole('SPECIALIST')")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") final long id) {
        deleteProductUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER') or hasRole('SPECIALIST')")
    public ResponseEntity<Void> updateProduct(@PathVariable("id") long id,
                                                  @RequestBody @Valid UpdateProductRequest request) {
        request.setId(id);
        updateProductUseCase.updateProduct(request);
        return ResponseEntity.noContent().build();

    }
}