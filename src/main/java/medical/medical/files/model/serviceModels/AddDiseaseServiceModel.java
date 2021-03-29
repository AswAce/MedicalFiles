package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enteties.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddDiseaseServiceModel extends BaseEntity {

    private long patientId;
    private long examinationId;
    private String name;

    private String type;


    private String description;


    private boolean isCurable;

    private boolean isChronic;


}
