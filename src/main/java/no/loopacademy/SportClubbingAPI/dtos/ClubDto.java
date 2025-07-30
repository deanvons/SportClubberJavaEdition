package no.loopacademy.SportClubbingAPI.dtos;

import lombok.Data;

@Data
public class ClubDto {
    private int id;

    @NotBlank
    @Size(max = 100)
    private String name;

    private String location;

    private int foundedYear;
}
