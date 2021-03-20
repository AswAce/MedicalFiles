package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.ProgressionEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "examinations")
public class ExaminationEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private MedicalBranchesEnum typeOfBranch;
    private LocalDate date;
    @ManyToOne
    private DoctorEntity doctor;

    @Column(columnDefinition = "TEXT")
    private String complain;

    @OneToOne(cascade = CascadeType.ALL)
    private PrescriptionEntity prescription;

    @OneToOne(cascade = CascadeType.ALL)
    private LocationEntity location;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AdditionalDataEntity> additionalData = new ArrayList<>();



    @Enumerated(EnumType.STRING)
    private ProgressionEnum progression;


}
