package no.loopacademy.SportClubbingAPI.services;

import jakarta.persistence.EntityNotFoundException;
import no.loopacademy.SportClubbingAPI.exceptions.NotFoundException;
import no.loopacademy.SportClubbingAPI.models.Club;
import no.loopacademy.SportClubbingAPI.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubServiceImpl implements ClubService{

    @Autowired
    private ClubRepository clubRepository;

    /**
     * @return All clubs entities in the database
     */
    @Override
    public List<Club> getAll() {
        return clubRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Club getById(int id) throws NotFoundException {
        return clubRepository.findById(id).orElseThrow(() -> new NotFoundException("Club not found with id: " + id));
    }

    /**
     * @param newClub
     * @return
     */
    @Override
    public Club add(Club newClub) {
        return clubRepository.save(newClub);
    }

    /**
     * @param existingClub
     * @return
     */
    @Override
    public Club update(Club existingClub) throws NotFoundException {
        if(clubRepository.existsById(existingClub.getId())) {
            return clubRepository.save(existingClub);
        } else {
            throw new NotFoundException("Club not found with id: " + existingClub.getId());
        }
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(int id) throws NotFoundException {
        if(clubRepository.existsById(id)) {
            // TODO: Set relationships to null, OR reject deletion if a team is in the club
            clubRepository.deleteById(id);
        } else {
            throw new NotFoundException("Club not found with id: " + id);
        }
    }
}
