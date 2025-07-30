package no.loopacademy.SportClubbingAPI.services;

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
    public Club getById(int id) {
        // TODO: Throw custom NotFound exception
        return clubRepository.findById(id).orElseThrow(RuntimeException::new);
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
    public Club update(Club existingClub) throws RuntimeException {
        if(clubRepository.existsById(existingClub.getId())) {
            return clubRepository.save(existingClub);
        } else {
            // TODO: Replace with custom NotFound exception
            throw new RuntimeException("No entity exists with that ID");
        }
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(int id) throws RuntimeException {
        if(clubRepository.existsById(id)) {
            // TODO: Set relationships to null, OR reject deletion if a team is in the club
            clubRepository.deleteById(id);
        } else {
            // TODO: Replace with custom NotFound exception
            throw new RuntimeException("No entity exists with that ID");
        }
    }
}
