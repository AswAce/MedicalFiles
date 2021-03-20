package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enteties.LocationEntity;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.ProgressionEnum;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddExaminationServiceModel {

    private long patientId;

    private MedicalBranchesEnum typeOfBranch;

    private LocalDate date;

    private String doctorName;

    private String complain;


}
