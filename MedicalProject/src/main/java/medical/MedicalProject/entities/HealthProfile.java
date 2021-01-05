package medical.MedicalProject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "health_profile")
public class HealthProfile extends IdBaseClass {

    @OneToOne( )
    private User user;

    private int age;

    private double height;

    private double weight;
    private double bdi;


    @ManyToMany(fetch = FetchType.EAGER,targetEntity = Disease.class)
    @JoinTable(name = "health_profiles_diseases",
            joinColumns = @JoinColumn(name = "health_profile_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id", referencedColumnName = "id")
    )
    private Set<Disease> diseases;

//    @OneToMany(fetch = FetchType.EAGER,targetEntity = MedicalCheck.class, mappedBy = "healthProfile", cascade = CascadeType.ALL)
//    private Set<MedicalCheck> visitations;

//    @OneToMany(fetch = FetchType.LAZY,targetEntity = BloodResult.class, mappedBy = "healthProfile", cascade = CascadeType.ALL)
//    private Set<BloodResult> bloodResults;


}
