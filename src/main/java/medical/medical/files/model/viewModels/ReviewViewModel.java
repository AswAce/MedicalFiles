package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.enteties.PatientEntity;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewViewModel {

    private long id;
    private String patientName;

    private int rating;
    private int leftRating;
    private String comment;

    private MedicalBranchesEnum department;

    private String doctorName;

    private String localDateTime;
}
