package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.ProgressionEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddExaminationBindingModel {


    @NotNull(message = "Choose departments")
    private MedicalBranchesEnum typeOfBranch;
    @FutureOrPresent(message = "Date must be in future or present")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;
    @NotEmpty(message = "Choose doctor name")
    private String doctorName;
    @NotEmpty
    @Size(min = 10,max = 150,message = "enter your complain")
    private String complain;


}
