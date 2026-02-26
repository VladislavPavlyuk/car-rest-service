package ua.com.foxmined.carrestservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CarApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCars_WhenPaginationParamsProvided_ReturnsOkWithJsonList() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("page", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getCars_WhenManufacturerAndModelFiltersProvided_ReturnsOkWithFilteredJsonList() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("manufacturer", "Audi")
                        .param("model", "Q3")
                        .param("page", "0")
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getCategories_WhenPaginationParamsProvided_ReturnsOkWithJsonList() throws Exception {
        mockMvc.perform(get("/api/v1/categories")
                        .param("page", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getManufacturers_WhenPaginationParamsProvided_ReturnsOkWithJsonList() throws Exception {
        mockMvc.perform(get("/api/v1/manufacturers")
                        .param("page", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
