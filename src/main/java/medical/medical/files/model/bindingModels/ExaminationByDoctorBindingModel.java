package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enteties.AdditionalDataEntity;
import medical.medical.files.model.enteties.LocationEntity;
import medical.medical.files.model.enteties.PrescriptionEntity;
import medical.medical.files.model.enums.PartOfTheBodyEnum;
import medical.medical.files.model.enums.SideOfTheBodyEnum;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationByDoctorBindingModel {

    private long doctorId;

    @NotBlank
    private PartOfTheBodyEnum partOfTheBody;
    @NotBlank
    private SideOfTheBodyEnum sideOfTheBody;

    @NotBlank
    private String exactLocation;


}
