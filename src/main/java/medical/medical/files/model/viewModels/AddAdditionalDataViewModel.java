package medical.medical.files.model.viewModels;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.TypeEnum;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAdditionalDataViewModel {
    private LocalDate date;


    private TypeEnum type;


    private String file;

    private LocationViewModel location;
}
