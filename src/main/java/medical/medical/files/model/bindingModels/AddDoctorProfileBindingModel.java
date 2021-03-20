package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import medical.medical.files.model.enums.MedicalBranchesEnum;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDoctorProfileBindingModel {

    private static final String WORKING_PATTERN = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]-([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
    @NotNull
    private MedicalBranchesEnum medicalBranch;
    @NotBlank(message = "enter name")
    private String fullName;
    @NotEmpty(message = "enter Bio")
    @Size(min = 10, message = "Bio must be at least 10 chars")
    private String bio;

    @NotBlank(message = "enter valid phone")
    @Pattern(regexp = "^0\\d{3}-\\d{2}-\\d{2}-\\d{2}$", message = "Enter valid phone 0877-11-22-33")
    private String phone;


    private MultipartFile img;

    @NotBlank(message = "Enter your Cabinet")
    @Pattern(regexp = "^[A-Z]\\d{1,2}", message = "Enter valid Cabinet A22 , B1 ...")
    private String roomNumber;

    @DecimalMin(value = "0", message = "enter valid years of Experience")
    private int experience;


    @Pattern(regexp = WORKING_PATTERN)
    private String monday;

    @Pattern(regexp = WORKING_PATTERN)
    private String tuesday;

    @Pattern(regexp = WORKING_PATTERN)
    private String wednesday;

    @Pattern(regexp = WORKING_PATTERN)
    private String thursday;

    @Pattern(regexp = WORKING_PATTERN)
    private String friday;


}
