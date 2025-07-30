package no.loopacademy.SportClubbingAPI.controllers;

import jakarta.validation.Valid;
import no.loopacademy.SportClubbingAPI.dtos.ClubDto;
import no.loopacademy.SportClubbingAPI.mappers.ClubMapper;
import no.loopacademy.SportClubbingAPI.models.Club;
import no.loopacademy.SportClubbingAPI.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubMapper clubMapper;

    /**
     * Get all clubs
     */
    @GetMapping
    public ResponseEntity<List<ClubDto>> getAllClubs() {
        List<Club> clubs = clubService.getAll();
        List<ClubDto> clubDtos = clubMapper.clubListToDtoList(clubs);
        return ResponseEntity.ok(clubDtos);
    }

    /**
     * Get a single club by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClubDto> getClubById(@PathVariable int id) {
        Club club = clubService.getById(id);
        ClubDto dto = clubMapper.clubToClubDto(club);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new club
     */
    @PostMapping
    public ResponseEntity<ClubDto> createClub(@Valid @RequestBody ClubDto newClubDto) {
        Club club = clubMapper.clubDtoToClub(newClubDto);
        Club savedClub = clubService.add(club);
        ClubDto responseDto = clubMapper.clubToClubDto(savedClub);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * Update an existing club
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClubDto> updateClub(@PathVariable int id, @Valid @RequestBody ClubDto updatedClubDto) {
        if (id != updatedClubDto.getId()) {
            return ResponseEntity.badRequest().build(); // ID mismatch safeguard
        }

        Club club = clubMapper.clubDtoToClub(updatedClubDto);
        Club updatedClub = clubService.update(club);
        ClubDto responseDto = clubMapper.clubToClubDto(updatedClub);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Delete a club by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable int id) {
        clubService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
