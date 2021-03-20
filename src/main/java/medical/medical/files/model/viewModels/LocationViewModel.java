package medical.medical.files.model.viewModels;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.PartOfTheBodyEnum;
import medical.medical.files.model.enums.SideOfTheBodyEnum;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationViewModel {
    private PartOfTheBodyEnum partOfTheBody;

    private SideOfTheBodyEnum sideOfTheBody;

    private String exactLocation;
}
