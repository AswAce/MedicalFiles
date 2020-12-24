package medical.MedicalProject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "visitations")
public class Visitation extends IdBaseClass {
    private String name;
    private String diagnose;

    private LocalDateTime date;


    @OneToOne
    private VisualResult visualResult;

    @ManyToOne(targetEntity = Location.class)
    @JoinColumn(name = "injury_location_id")
    private Location injuryLocation;


    @Column(name = "recovery_time")
    private int recoveryTime;


    @ManyToOne(targetEntity = HealthProfile.class)
    @JoinColumn(name = "profile_id")
    private HealthProfile healthProfile;


    @ManyToMany(targetEntity = NegativeImpact.class)
    @JoinTable(name = "visitation_negativeImpact",
            joinColumns = @JoinColumn(name = "visitation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "negativeImpact_Id", referencedColumnName = "id")
    )

    private Set<NegativeImpact> negativeImpacts;
}
