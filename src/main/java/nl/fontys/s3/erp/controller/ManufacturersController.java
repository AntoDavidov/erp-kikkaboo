package nl.fontys.s3.erp.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ManufacturerUseCases.*;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.business.DTOs.CreateManufacturerRequest;
import nl.fontys.s3.erp.business.DTOs.CreateManufacturerResponse;
import nl.fontys.s3.erp.business.DTOs.GetManufacturersResponse;
import nl.fontys.s3.erp.business.DTOs.UpdateManufacturerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Manufacturers")
@AllArgsConstructor
public class ManufacturersController {
    private final CreateManufacturerUseCase createManufacturerUseCase;
    private final GetManufacturersUseCase getManufacturersUseCase;
    private final DeleteManufacturerUseCase deleteManufacturerUseCase;
    private final GetManufacturerUseCase getManufacturerUseCase;
    private final UpdateManufacturerUseCase updateManufacturerUseCase;

    @GetMapping()
    public ResponseEntity<GetManufacturersResponse> getManufacturers() {
        return ResponseEntity.ok(getManufacturersUseCase.getManufacturers());
    }

    @PostMapping()
    public ResponseEntity<CreateManufacturerResponse> createManufacturer(@RequestBody @Valid CreateManufacturerRequest request) {
        CreateManufacturerResponse response = createManufacturerUseCase.createManufacturer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable int id){
        deleteManufacturerUseCase.deleteManufacturer(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("{id}")
    public ResponseEntity<Manufacturer> getManufacturers(@PathVariable(value = "id") final long id) {
        final Manufacturer manufacturer = getManufacturerUseCase.getManufacturer(id);
        if(manufacturer == null){
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok().body(manufacturer);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateManufacturer(@PathVariable(value = "id") long id, @RequestBody @Valid UpdateManufacturerRequest request) {
        request.setManufacturerId(id);
        updateManufacturerUseCase.updateManufacturer(request);
        return ResponseEntity.noContent().build();
    }


}
