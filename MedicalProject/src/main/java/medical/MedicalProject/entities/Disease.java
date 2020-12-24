package medical.MedicalProject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diseases")
public class Disease extends IdBaseClass {
    private String name;
    private String details;
    @Column(name = "is_curable")
    private boolean isCurable;

    @ManyToMany(targetEntity = NegativeImpact.class)
    @JoinTable(name = "disease_negativeImpact",
            joinColumns = @JoinColumn(name = "disease_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "negativeImpact_Id", referencedColumnName = "id")
    )
    @Column(name = "negative_impact")
    private Set<NegativeImpact> negativeImpacts;

    @ManyToMany(targetEntity = HealthProfile.class, mappedBy = "diseases")
    private Set<HealthProfile> healthProfiles;

    @ManyToOne(targetEntity = Location.class)
    @JoinColumn(name = "disease_location_id")
    private Location diseaseLocation;

    @OneToOne

    private VisualResult visualResult;

}
