package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.DrinkingEnum;
import medical.medical.files.model.enums.ExerciseEnum;
import medical.medical.files.model.enums.SexEnum;
import medical.medical.files.model.enums.SmokingEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientViewModel {

    private long userId;

    private int age;


    private double height;


    private double weight;


    private SexEnum sex;


    private SmokingEnum smoking;


    private DrinkingEnum drinking;


    private ExerciseEnum exercise;

    ArrayList<ExaminationViewModel> examinationViewModels = new ArrayList<>();

    ArrayList<DiseaseViewModel> diseaseViewModels=new ArrayList<>();

}
