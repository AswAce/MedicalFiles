package medical.MedicalProject.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "locations")
public class Location extends IdBaseClass {
    @Column(name = "part_of_the_body")
    @NonNull
    private String partOfTheBody;
    @OneToMany(targetEntity = MedicalCheck.class, mappedBy = "injuryLocation")
    private Set<MedicalCheck> visitations;
    @OneToMany(targetEntity = MedicalCheck.class, mappedBy = "injuryLocation")
    private Set<Disease> diseases;

}
