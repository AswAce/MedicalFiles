package medical.medical.files.model.viewModels;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseViewModel {

    private String name;

    private String type;

    private String description;

    private boolean isCurable;

    private boolean isChronic;
}
