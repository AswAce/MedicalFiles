package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSetViewModel {
    private long id;
    private String fullName;
    private MedicalBranchesEnum medicalBranchesEnum;
    private String photo;
    // TODO: 21-Mar-21   private String reviews;

}
