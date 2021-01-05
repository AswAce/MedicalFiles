package medical.MedicalProject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BloodResult extends IdBaseClass {

    private LocalDateTime date;
    private String result;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = HealthProfile.class)
    private HealthProfile healthProfile;

}
