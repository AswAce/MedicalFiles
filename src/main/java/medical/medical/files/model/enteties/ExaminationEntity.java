package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.ProgressionEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "examinations")
public class ExaminationEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private MedicalBranchesEnum typeOfBranch;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER )
    private DoctorEntity doctor;
    @ManyToOne(fetch = FetchType.EAGER)
    private PatientEntity patient;

    @Column(columnDefinition = "TEXT")
    private String complain;

    @OneToOne(cascade = CascadeType.ALL)
    private PrescriptionEntity prescription;

    @OneToOne(cascade = CascadeType.ALL)
    private LocationEntity location;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AdditionalDataEntity> additionalData = new LinkedHashSet<>();


    @Enumerated(EnumType.STRING)
    private ProgressionEnum progression;

    @Override
    public String toString() {
        return "ExaminationEntity{" +
                "typeOfBranch=" + typeOfBranch +
                ", date=" + date +
                ", complain='" + complain + '\'' +
                ", prescription=" + prescription +
                ", progression=" + progression +
                '}';
    }
}
