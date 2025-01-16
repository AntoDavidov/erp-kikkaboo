package nl.fontys.s3.erp.business.impl.manufacturerunittests;

import nl.fontys.s3.erp.business.dtos.manufacturerdto.CreateManufacturerRequest;
import nl.fontys.s3.erp.business.dtos.manufacturerdto.CreateManufacturerResponse;
import nl.fontys.s3.erp.business.dtos.manufacturerdto.GetManufacturersResponse;
import nl.fontys.s3.erp.business.dtos.manufacturerdto.UpdateManufacturerRequest;
import nl.fontys.s3.erp.business.manufacturerusecases.*;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.domain.users.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ManufacturerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateManufacturerUseCase createManufacturerUseCase;

    @MockBean
    private GetManufacturersUseCase getManufacturersUseCase;

    @MockBean
    private DeleteManufacturerUseCase deleteManufacturerUseCase;

    @MockBean
    private GetManufacturerUseCase getManufacturerUseCase;

    @MockBean
    private UpdateManufacturerUseCase updateManufacturerUseCase;

    @Test
    @WithMockUser(roles = "CEO")
    public void shouldGetAllManufacturers() throws Exception {
        // Arrange
        GetManufacturersResponse response = new GetManufacturersResponse(
                Collections.singletonList(new Manufacturer(1L, "Manufacturer A", Country.BULGARIA, "Plovdiv", Status.ACTIVE))
        );
        when(getManufacturersUseCase.getManufacturers()).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/manufacturers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.manufacturers[0].id").value(1))
                .andExpect(jsonPath("$.manufacturers[0].companyName").value("Manufacturer A"))
                .andExpect(jsonPath("$.manufacturers[0].country").value("BULGARIA"));
    }

    @Test
    @WithMockUser(roles = "CEO")
    public void shouldReturnNotFoundForNonExistentManufacturer() throws Exception {
        // Arrange
        when(getManufacturerUseCase.getManufacturer(999L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/manufacturers/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(roles = "CEO")
    public void shouldCreateManufacturer() throws Exception {
        // Arrange
        CreateManufacturerRequest request = new CreateManufacturerRequest(Country.BULGARIA, "Manufacturer A", "Plovdiv");
        CreateManufacturerResponse response = new CreateManufacturerResponse(2L);
        when(createManufacturerUseCase.createManufacturer(any(CreateManufacturerRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/manufacturers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "companyName": "Manufacturer A",
                      "country": "BULGARIA",
                      "city": "Plovdiv"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.manufacturerId").value(2));
    }

    @Test
    @WithMockUser(roles = "CEO")
    public void shouldFailToCreateManufacturerWithInvalidData() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/manufacturers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "name": "",
                      "country": "Country B"
                    }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "CEO")
    public void shouldUpdateManufacturer() throws Exception {
        // Arrange
        doNothing().when(updateManufacturerUseCase).updateManufacturer(any(UpdateManufacturerRequest.class));

        // Act & Assert
        mockMvc.perform(put("/manufacturers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "companyName": "Updated Manufacturer",
                      "country": "BULGARIA",
                      "city": "Sofia",
                      "status": "ACTIVE"
                    }
                """))
                .andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser(roles = "CEO")
    public void shouldDeleteManufacturer() throws Exception {
        // Arrange
        doNothing().when(deleteManufacturerUseCase).deleteManufacturer(1);

        // Act & Assert
        mockMvc.perform(delete("/manufacturers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void shouldReturnForbiddenWhenUnauthorized() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/manufacturers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}
