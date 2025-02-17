package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "medical_branches")
public class MedicalBranchEntity extends BaseEntity {


    @Enumerated(EnumType.STRING)
    private MedicalBranchesEnum name;
    @Column(name = "descriptions", columnDefinition = "TEXT")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<DoctorEntity> doctors = new LinkedHashSet<>();
    private String photo;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ScheduleEntity schedule;

    @Override
    public String toString() {
        return "MedicalBranchEntity{" +
                "name=" + name +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
