package medical.MedicalProject.Services.SeedDto.SeedNegativeImpact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NegativeImpactRootDto {

    private Set<NegativeImpactDto> negativeImpactDtoSet;
}
