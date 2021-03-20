package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.DrinkingEnum;
import medical.medical.files.model.enums.ExerciseEnum;
import medical.medical.files.model.enums.SexEnum;
import medical.medical.files.model.enums.SmokingEnum;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientBindingModel {


    private long userId;
    @Min(0)
    private int age;

    @Min(100)
    private double height;

    @Min(20)
    private double weight;

    @NotNull
    private SexEnum sex;
    private String fullName;
    @NotNull
    private SmokingEnum smoking;

    @NotNull
    private DrinkingEnum drinking;

    @NotNull
    private ExerciseEnum exercise;

    private Map<String, String> errors = new LinkedHashMap<>();


}
