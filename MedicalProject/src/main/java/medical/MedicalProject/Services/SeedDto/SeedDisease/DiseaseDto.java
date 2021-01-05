package medical.MedicalProject.Services.SeedDto.SeedDisease;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.MedicalProject.Services.SeedDto.SeedVisualResult.VisualResultDto;

import javax.persistence.Column;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseDto {
    @Expose
    private String name;
    @Expose
    private String details;
    @Expose
    private boolean isCurable;
    @Expose
    private String medicineBranch;
    @Expose
    private String locations;
    @Expose
    private ArrayList<String> negativeImpact;
    @Expose
    private VisualResultDto visualResultDto;

}
