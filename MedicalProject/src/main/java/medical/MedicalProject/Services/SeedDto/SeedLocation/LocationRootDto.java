package medical.MedicalProject.Services.SeedDto.SeedLocation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRootDto {

    private Set<LocationSeedDto> locationSeedDtoSet;
}
