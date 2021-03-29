package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.ProgressionEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationViewModel {

    private long id;
    private MedicalBranchesEnum typeOfBranch;
    private LocalDateTime date;
    private long patientId;
    private long doctorId;
    private String doctorFullName;



    private String complain;


    private PrescriptionViewModel prescription;


    private LocationViewModel location;


    private List<AddAdditionalDataViewModel> additionalData = new ArrayList<>();

    private ProgressionEnum progressionEnum;

}
