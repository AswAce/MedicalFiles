package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enteties.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddDiseaseBindingModel extends BaseEntity {

    @NotBlank
    private String name;

    private String type;

    private String isCurable;

    private String isChronic;

    @Size(min = 3, message = "Enter description")
    private String description;

}
