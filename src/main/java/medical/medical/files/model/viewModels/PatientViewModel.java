package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientViewModel {

    private long userId;

    private int age;

    private String fullName;
    private double height;

    private String photo;
    private double weight;
    private String phone;
    private String bmi;


    private SexEnum sex;


    private SmokingEnum smoking;


    private DrinkingEnum drinking;


    private ExerciseEnum exercise;

    ArrayList<ExaminationViewModel> examinationViewModels = new ArrayList<>();

    ArrayList<DiseaseViewModel> diseaseViewModels = new ArrayList<>();


}
