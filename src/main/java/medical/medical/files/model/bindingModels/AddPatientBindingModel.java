package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.DrinkingEnum;
import medical.medical.files.model.enums.ExerciseEnum;
import medical.medical.files.model.enums.SexEnum;
import medical.medical.files.model.enums.SmokingEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPatientBindingModel {


    @Max(value = 100, message = "Enter valid age")
    private int age;


    @DecimalMin(value = "0.50", message = "Enter valid height")
    @DecimalMax(value = "2.50", message = "Enter valid height")
    private double height;


    @Min(value = 20, message = "Enter valid weight")
    @Max(value = 200, message = "Enter valid weight")
    private double weight;

    @NotBlank(message = "enter valid phone")
    @Pattern(regexp = "^0\\d{3}-\\d{2}-\\d{2}-\\d{2}$", message = "Enter valid phone 0877-11-22-33")
    private String phone;

    @NotNull
    private SexEnum sex;
    @NotBlank(message = "enter name")

    private String fullName;

    @NotNull
    private SmokingEnum smoking;

    @NotNull
    private DrinkingEnum drinking;

    @NotNull
    private ExerciseEnum exercise;


    private MultipartFile img;


}
