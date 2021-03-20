package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.ProgressionEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationViewModel {

    private MedicalBranchesEnum typeOfBranch;
    private LocalDate date;

    private String doctorFullName;


    private String complain;


    private PrescriptionViewModel prescription;


    private LocationViewModel location;


    private List<AddAdditionalDataViewModel> additionalData = new ArrayList<>();


    private FeedbackEntity feedback;


    private ProgressionEnum progression;

}
