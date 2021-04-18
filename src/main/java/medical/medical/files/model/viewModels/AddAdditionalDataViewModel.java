package medical.medical.files.model.viewModels;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.TypeEnum;
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
