package medical.MedicalProject.Services.SeedDto.SeedMedicineBranches;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class MedicineBranchesRootDto {

    private Set<MedicineBranchesSeedDto> medicineBranchesSeedDtoSet;
}
