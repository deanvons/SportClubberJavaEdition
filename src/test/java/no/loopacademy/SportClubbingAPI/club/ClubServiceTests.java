package no.loopacademy.SportClubbingAPI.club;

import no.loopacademy.SportClubbingAPI.models.Club;
import no.loopacademy.SportClubbingAPI.repositories.ClubRepository;
import no.loopacademy.SportClubbingAPI.services.ClubService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

// Wires everything up like a real application
@SpringBootTest
// Sets active profile to 'test'
@ActiveProfiles("test")
public class ClubServiceTests {

    @Autowired
    private ClubService clubService;

    @BeforeAll
    static void setUp() {
        // TODO: Add any dummy data in addition to data-test.sql if needed
    }

    @Test
    void shouldFetchSeededClub() {
        // Arrange
        String expectedClubName = "Stormers";

        // Act
        Club actualClub = clubService.getById(1);
        String actualClubName = actualClub.getName();

        // Assert
        assertEquals(expectedClubName, actualClubName);
    }

    @Test
    void shouldCreateNewClub() {
        // Arrange
        String expectedName = "Sharks";
        String expectedLocation = "Durban";
        int expectedYear = 1960;

        Club newClub = new Club();
        newClub.setName(expectedName);
        newClub.setLocation(expectedLocation);
        newClub.setFoundedYear(expectedYear);

        // Act
        Club actualClub = clubService.add(newClub);

        // Assert
        assertEquals(expectedName, actualClub.getName());
        assertEquals(expectedLocation, actualClub.getLocation());
        assertEquals(expectedYear, actualClub.getFoundedYear());
    }

    @Test
    void shouldUpdateExistingClub() throws RuntimeException {
        // Arrange
        String updatedLocation = "Cape Town";
        Club existingClub = clubService.getById(1);
        existingClub.setLocation(updatedLocation);

        // Act
        Club actualClub = clubService.update(existingClub);

        // Assert
        assertEquals(updatedLocation, actualClub.getLocation());
    }

    @Test
    void shouldReturnAllClubs() {
        // Arrange
        int expectedMinSize = 1; // From seeded data

        // Act
        List<Club> actualClubs = clubService.getAll();
        int actualSize = actualClubs.size();

        // Assert
        assertTrue(actualSize >= expectedMinSize);
    }

    @Test
    void shouldDeleteClubById() throws RuntimeException {
        // Arrange
        Club clubToDelete = new Club();
        clubToDelete.setName("Bulls");
        clubToDelete.setLocation("Pretoria");
        clubToDelete.setFoundedYear(1990);
        Club saved = clubService.add(clubToDelete);
        int idToDelete = saved.getId();

        // Act
        clubService.deleteById(idToDelete);

        // Assert
        assertThrows(RuntimeException.class, () -> clubService.getById(idToDelete));
    }

    @Test
    void shouldThrowWhenGettingNonExistentClub() {
        // Arrange
        int nonExistentId = 999;

        // Act + Assert
        assertThrows(RuntimeException.class, () -> clubService.getById(nonExistentId));
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentClub() {
        // Arrange
        Club fakeClub = new Club();
        fakeClub.setId(999);
        fakeClub.setName("NonExistent");
        fakeClub.setLocation("Nowhere");
        fakeClub.setFoundedYear(2024);

        // Act + Assert
        assertThrows(RuntimeException.class, () -> clubService.update(fakeClub));
    }

    @Test
    void shouldThrowWhenDeletingNonExistentClub() {
        // Arrange
        int nonExistentId = 999;

        // Act + Assert
        assertThrows(RuntimeException.class, () -> clubService.deleteById(nonExistentId));
    }

}
