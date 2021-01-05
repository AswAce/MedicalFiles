package medical.MedicalProject.Services.SeedDto.SeedNegativeImpact;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NegativeImpactDto {
    @Expose
    private String type;

    @Expose

    private String painLevel;

    @Expose
    @Min(value = 0, message = "danger level must be positive number")
    @Max(value = 10, message = "danger level maximum is 10")
    private int dangerLevel;
}
