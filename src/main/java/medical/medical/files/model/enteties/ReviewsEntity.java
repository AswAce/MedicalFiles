package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "feedback")
public class ReviewsEntity extends BaseEntity {


    @ManyToOne
    private PatientEntity patient;
    @Column(nullable = false)
    private int rating;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String comment;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MedicalBranchesEnum department;
    @ManyToOne
    private DoctorEntity doctor;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime localDateTime;


}
