package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPrescriptionServiceModel {


    private long examinationId;
    private String doctorPrescription;


    private int days;
}
