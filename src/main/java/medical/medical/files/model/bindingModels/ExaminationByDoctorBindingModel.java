package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import medical.medical.files.model.enums.PartOfTheBodyEnum;
import medical.medical.files.model.enums.SideOfTheBodyEnum;


import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationByDoctorBindingModel {


    @NotNull
    private PartOfTheBodyEnum partOfTheBody;
    @NotNull
    private SideOfTheBodyEnum sideOfTheBody;

    private String exactLocation;


}
