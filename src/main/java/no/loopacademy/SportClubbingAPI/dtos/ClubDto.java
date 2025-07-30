package no.loopacademy.SportClubbingAPI.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// TODO: Split into operational DTOs (Read, Add, Edit)
@Data
public class ClubDto {
    private int id;

    @NotBlank
    @Size(max = 100)
    private String name;

    private String location;

    private int foundedYear;
}
