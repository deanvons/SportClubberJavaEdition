package no.loopacademy.SportClubbingAPI.mappers;

import no.loopacademy.SportClubbingAPI.dtos.ClubDto;
import no.loopacademy.SportClubbingAPI.models.Club;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClubMapper {
    ClubDto clubToClubDto(Club club);
    Club clubDtoToClub(ClubDto club);
    List<ClubDto> clubListToDtoList(List<Club> clubs);
}
