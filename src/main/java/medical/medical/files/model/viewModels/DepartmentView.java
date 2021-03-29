package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentView {
    private long id;
    private long doctorId;
    private MedicalBranchesEnum medicalBranchesEnum;
    private String description;
    private String photo;
    private String firstDoctorName;

    public String getLittleBit() {
        return this.description.substring(0, 30) + "...";
    }
}
