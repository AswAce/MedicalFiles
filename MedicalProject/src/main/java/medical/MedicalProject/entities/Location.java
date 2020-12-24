package medical.MedicalProject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "locations")
public class Location extends IdBaseClass {
    @Column(name = "part_of_the_body")
    private String partOfTheBody;

    @OneToMany(targetEntity = Visitation.class, mappedBy = "injuryLocation")
    private Set<Visitation> visitations;

    @OneToMany(targetEntity = Visitation.class, mappedBy = "injuryLocation")
    private Set<Disease> diseases;

}
