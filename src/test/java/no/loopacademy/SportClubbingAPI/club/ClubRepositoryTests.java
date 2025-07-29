package no.loopacademy.SportClubbingAPI.club;

import no.loopacademy.SportClubbingAPI.models.Club;
import no.loopacademy.SportClubbingAPI.repositories.ClubRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

// With DataJpaTest, we don't need to configure our db or anything,
// it does it by default, but we still have it explicitly done for reference.
@DataJpaTest
@ActiveProfiles("test")
public class ClubRepositoryTests {

    @Autowired
    private ClubRepository clubRepository;

    @Test
    void shouldFetchSeededClub() {
        // Arrange
        String expectedClubName = "Stormers";

        // Act
        Club seededClub = clubRepository.findById(1).orElseThrow();
        String actualClubName = seededClub.getName();

        // Assert
        assertEquals(expectedClubName,actualClubName);
    }

    @Test
    void shouldCreateNewClub() {
        // Arrange
        String expectedNewClubName = "Sharks";
        String expectedNewClubLocation = "Durban";
        int expectedNewClubFoundedYear = 1960;

        // Act

        // Assert


    }
}
