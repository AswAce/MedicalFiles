package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleDepartmentView {
    private MedicalBranchesEnum name;
    private String description;
    private String photo;
    private Set<ReviewViewModel> reviews = new HashSet<>();
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String headDoctor;


}
