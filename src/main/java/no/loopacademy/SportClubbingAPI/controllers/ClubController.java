package no.loopacademy.SportClubbingAPI.controllers;

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

    /**
     * Get all clubs
     */
    @GetMapping
    public ResponseEntity<List<Club>> getAllClubs() {
        return ResponseEntity.ok(clubService.getAll());
    }

    /**
     * Get a single club by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable int id) {
        Club club = clubService.getById(id);
        return ResponseEntity.ok(club);
    }

    /**
     * Create a new club
     */
    @PostMapping
    public ResponseEntity<Club> createClub(@RequestBody Club newClub) {
        Club savedClub = clubService.add(newClub);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClub);
    }

    /**
     * Update an existing club
     * Ensures URL id matches the entity id
     */
    @PutMapping("/{id}")
    public ResponseEntity<Club> updateClub(@PathVariable int id, @RequestBody Club updatedClub) {
        if (id != updatedClub.getId()) {
            return ResponseEntity.badRequest().build(); // ID mismatch safeguard
        }
        Club savedClub = clubService.update(updatedClub);
        return ResponseEntity.ok(savedClub);
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
