package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionBindingModel {
    @NotBlank
    private long doctorId;
    @NotBlank
    private String doctorPrescription;
    @NotBlank
    private int days;
}
