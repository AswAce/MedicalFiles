package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewViewModel {

    private long id;
    private String patientName;

    private int rating;
    private int leftRating;
    private String comment;

    private MedicalBranchesEnum department;

    private String doctorName;

    private String localDateTime;
}
