package medical.MedicalProject.Services.SeedDto.SeedVisualResult;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
public class VisualResultDto {
    @Expose
    private String type;
    @Expose
    private String picture;


}
