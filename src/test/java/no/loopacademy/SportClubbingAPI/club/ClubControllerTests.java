package no.loopacademy.SportClubbingAPI.club;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.loopacademy.SportClubbingAPI.models.Club;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClubControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // for JSON serialization

    private Club testClub;

    @BeforeEach
    void setUp() {
        testClub = new Club();
        testClub.setName("Bulls");
        testClub.setLocation("Pretoria");
        testClub.setFoundedYear(1990);
    }

    @Test
    void shouldGetAllClubs() throws Exception {
        mockMvc.perform(get("/api/clubs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].name", is("Stormers"))); // from data-test.sql
    }

    @Test
    void shouldGetClubById() throws Exception {
        mockMvc.perform(get("/api/clubs/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Stormers")));
    }

    @Test
    void shouldCreateNewClub() throws Exception {
        mockMvc.perform(post("/api/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testClub)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name", is("Bulls")))
                .andExpect(jsonPath("$.location", is("Pretoria")));
    }

    @Test
    void shouldUpdateExistingClub() throws Exception {
        // First create a club
        String response = mockMvc.perform(post("/api/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testClub)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Club savedClub = objectMapper.readValue(response, Club.class);
        savedClub.setLocation("Cape Town");

        // Then update it
        mockMvc.perform(put("/api/clubs/{id}", savedClub.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedClub)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location", is("Cape Town")));
    }

    @Test
    void shouldDeleteClub() throws Exception {
        // First create a club
        String response = mockMvc.perform(post("/api/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testClub)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Club savedClub = objectMapper.readValue(response, Club.class);

        // Then delete it
        mockMvc.perform(delete("/api/clubs/{id}", savedClub.getId()))
                .andExpect(status().isNoContent());

        // Verify it's gone
        mockMvc.perform(get("/api/clubs/{id}", savedClub.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnBadRequestOnMismatchedUpdateIds() throws Exception {
        testClub.setId(999); // intentionally wrong id
        mockMvc.perform(put("/api/clubs/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testClub)))
                .andExpect(status().isBadRequest());
    }
}
