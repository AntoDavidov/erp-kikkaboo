package nl.fontys.s3.erp.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ManufacturerUseCases.CreateManufacturerUseCase;
import nl.fontys.s3.erp.business.ManufacturerUseCases.GetManufacturersUseCase;
import nl.fontys.s3.erp.persistence.DTOs.CreateManufacturerRequest;
import nl.fontys.s3.erp.persistence.DTOs.CreateManufacturerResponse;
import nl.fontys.s3.erp.persistence.DTOs.GetManufacturersResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Manufacturers")
@AllArgsConstructor
public class ManufacturersController {
    private final CreateManufacturerUseCase createManufacturerUseCase;
    private final GetManufacturersUseCase getManufacturersUseCase;

    @GetMapping()
    public ResponseEntity<GetManufacturersResponse> getManufacturers() {
        return ResponseEntity.ok(getManufacturersUseCase.getManufacturers());
    }

    @PostMapping()
    public ResponseEntity<CreateManufacturerResponse> createManufacturer(@RequestBody @Valid CreateManufacturerRequest request) {
        CreateManufacturerResponse response = createManufacturerUseCase.createManufacturer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
