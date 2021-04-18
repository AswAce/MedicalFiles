package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import medical.medical.files.model.enums.MedicalBranchesEnum;

import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddExaminationServiceModel {

    private long patientId;

    private MedicalBranchesEnum typeOfBranch;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;


    private String doctorName;

    private String complain;


}
