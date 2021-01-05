package medical.MedicalProject.Services.SeedDto.SeedLocation;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationSeedDto {
    @Expose
    private String partOfTheBody;
}
