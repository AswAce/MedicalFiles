package medical.MedicalProject.Services.SeedDto.SeedMedicineBranches;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineBranchesSeedDto {
    @Expose
    private String name;
    @Expose
    private String description;
}
