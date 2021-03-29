package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPrescriptionBindingModel {


    @NotBlank
    @Size(min = 12,max = 100,message = "Enter valid prescription Doc ....")
    private String doctorPrescription;
    @NotNull
    private int days;
}
