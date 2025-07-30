package no.loopacademy.SportClubbingAPI.club;

import no.loopacademy.SportClubbingAPI.dtos.ClubDto;
import no.loopacademy.SportClubbingAPI.mappers.ClubMapper;
import no.loopacademy.SportClubbingAPI.models.Club;
import no.loopacademy.SportClubbingAPI.services.ClubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ClubMapperTests {

    @Autowired
    private ClubMapper clubMapper;

    @Test
    void shouldMapEntityToDto() {
        // Arrange
        Club club = new Club();
        club.setId(1);
        club.setName("Stormers");
        club.setLocation("Cape Town");
        club.setFoundedYear(1995);

        // Act
        ClubDto dto = clubMapper.clubToClubDto(club);

        // Assert
        assertEquals(club.getId(), dto.getId());
        assertEquals(club.getName(), dto.getName());
        assertEquals(club.getLocation(), dto.getLocation());
        assertEquals(club.getFoundedYear(), dto.getFoundedYear());
    }

    @Test
    void shouldMapEntityListToDtoList() {
        // Arrange
        Club club1 = new Club();
        club1.setId(1);
        club1.setName("Stormers");
        club1.setLocation("Cape Town");
        club1.setFoundedYear(1995);

        Club club2 = new Club();
        club2.setId(2);
        club2.setName("Sharks");
        club2.setLocation("Durban");
        club2.setFoundedYear(1960);

        List<Club> clubs = List.of(club1, club2);

        // Act
        List<ClubDto> dtos = clubMapper.clubListToDtoList(clubs);

        // Assert
        assertEquals(2, dtos.size());

        assertEquals("Stormers", dtos.get(0).getName());
        assertEquals("Cape Town", dtos.get(0).getLocation());

        assertEquals("Sharks", dtos.get(1).getName());
        assertEquals("Durban", dtos.get(1).getLocation());
    }

    @Test
    void shouldMapDtoToEntity() {
        // Arrange
        ClubDto dto = new ClubDto();
        dto.setId(3);
        dto.setName("Bulls");
        dto.setLocation("Pretoria");
        dto.setFoundedYear(1990);

        // Act
        Club club = clubMapper.clubDtoToClub(dto);

        // Assert
        assertEquals(dto.getId(), club.getId());
        assertEquals(dto.getName(), club.getName());
        assertEquals(dto.getLocation(), club.getLocation());
        assertEquals(dto.getFoundedYear(), club.getFoundedYear());
    }

}
