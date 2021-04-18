package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionViewModel {

    private String doctorPrescription;

    private int days;
}
