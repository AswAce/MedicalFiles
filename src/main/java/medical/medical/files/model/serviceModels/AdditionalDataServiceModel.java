package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.PartOfTheBodyEnum;
import medical.medical.files.model.enums.SideOfTheBodyEnum;
import medical.medical.files.model.enums.TypeEnum;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalDataServiceModel {

    private long doctorId;
    @NotBlank
    private LocalDate date;

    @NotBlank
    private TypeEnum type;

    @NotBlank
    private String file;

    @NotBlank
    private PartOfTheBodyEnum partOfTheBody;
    @NotBlank
    private SideOfTheBodyEnum sideOfTheBody;

    @NotBlank
    private String exactLocation;


}
