package no.loopacademy.SportClubbingAPI.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
