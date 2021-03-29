package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleDoctorView {

    private long id;
    private String fullName;
    private int experience;
    private MedicalBranchesEnum medicalBranchesEnum;
    private String room;
    private String bio;
    private String photo;
    private String phone;
    private Set<ReviewViewModel> reviews;

    private Map<String, String> workingDays = new LinkedHashMap<>();

}
