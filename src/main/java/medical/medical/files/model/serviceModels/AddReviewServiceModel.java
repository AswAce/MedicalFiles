package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewServiceModel {

    private String doctorName;
    private int rating;
    private String comment;
    private MedicalBranchesEnum department;

}
