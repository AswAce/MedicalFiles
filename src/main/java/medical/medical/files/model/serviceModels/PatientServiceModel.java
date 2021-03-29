package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.DrinkingEnum;
import medical.medical.files.model.enums.ExerciseEnum;
import medical.medical.files.model.enums.SexEnum;
import medical.medical.files.model.enums.SmokingEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientServiceModel {

    private int age;
    private String fullName;
    private double height;
    private String phone;
    private double weight;
    private SexEnum sex;
    private SmokingEnum smoking;
    private DrinkingEnum drinking;
    private ExerciseEnum exercise;
    private MultipartFile img;


}
