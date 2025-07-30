package no.loopacademy.SportClubbingAPI.services;

import no.loopacademy.SportClubbingAPI.models.Club;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClubService {
    List<Club> getAll();
    Club getById(int id);
    Club add(Club newClub);
    Club update(Club existingClub) throws RuntimeException;
    void deleteById(int id) throws RuntimeException;
}
