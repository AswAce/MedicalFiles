package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewBindingModel {

    @NotNull
    private String doctorName;
    @NotNull
    private int rating;
    @Size(min = 5, max = 50, message = "Enter comment")
    private String comment;

}
