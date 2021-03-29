package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.ProgressionEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetExaminationsForUserView {
    private long id;
    private String patientName;

    private LocalDateTime localDateTime;
    private String complain;
    private ProgressionEnum progressionEnum;
    private String patientPhoto;
    private String phone;
}
