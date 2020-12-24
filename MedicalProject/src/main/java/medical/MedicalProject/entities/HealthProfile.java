package medical.MedicalProject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "health_profile")
public class HealthProfile extends IdBaseClass {

    @OneToOne
    private User user;

    private int age;

    private double height;

    private double weight;
    private double bdi;


    @ManyToMany(targetEntity = Disease.class)
    @JoinTable(name = "health_profiles_diseases",
            joinColumns = @JoinColumn(name = "health_profile_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id", referencedColumnName = "id")
    )
    private Set<Disease> diseases;

    @OneToMany(targetEntity = Visitation.class, mappedBy = "healthProfile")
    private Set<Visitation> visitations;

    @OneToMany(targetEntity = BloodResult.class, mappedBy = "healthProfile")
    private Set<BloodResult> bloodResults;


    public HealthProfile(User user, int age, double height, double weight,
                         Set<Disease> diseases, Set<Visitation> visitations,
                         Set<BloodResult> bloodResults) {
        this.user = user;
        this.age = age;
        this.height = height;
        this.weight = weight;
        setBdi(this.weight, this.height);
        this.diseases = diseases;
        this.visitations = visitations;
        this.bloodResults = bloodResults;
    }

    public void setBdi(double weight, double height) {
        this.bdi = weight / (height * height);
    }
}
