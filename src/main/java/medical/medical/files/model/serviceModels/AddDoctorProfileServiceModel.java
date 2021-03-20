package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDoctorProfileServiceModel {



    private MedicalBranchesEnum medicalBranch;

    private String fullName;


    private String bio;


    private String phone;


    private MultipartFile pictureUrl;


    private String roomNumber;


    private int experience;



    private String monday;


    private String tuesday;


    private String wednesday;


    private String thursday;


    private String friday;


}
