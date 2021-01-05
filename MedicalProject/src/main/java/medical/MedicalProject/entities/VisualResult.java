package medical.MedicalProject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "visual_result")
public class VisualResult extends IdBaseClass {

    private String type;
    private LocalDateTime date;
    private String picture;

}
