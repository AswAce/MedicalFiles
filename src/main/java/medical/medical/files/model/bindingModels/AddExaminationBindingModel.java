package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.ProgressionEnum;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddExaminationBindingModel {

    private long patientId;
    @NotNull
    private MedicalBranchesEnum typeOfBranch;
    @FutureOrPresent
    private LocalDate date;
    @NotBlank
    private String doctorName;
    @NotBlank
    private String complain;




}
