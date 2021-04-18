package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.PartOfTheBodyEnum;
import medical.medical.files.model.enums.SideOfTheBodyEnum;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationByDoctorServiceModel {

    private long doctorId;
    private long examinationId;

    private PartOfTheBodyEnum partOfTheBody;

    private SideOfTheBodyEnum sideOfTheBody;


    private String exactLocation;


}
