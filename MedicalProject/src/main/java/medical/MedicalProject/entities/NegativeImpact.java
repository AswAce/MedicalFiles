package medical.MedicalProject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "negative_impacts")
public class NegativeImpact extends IdBaseClass {
    private String type;
    @Column(name = "pain_level")
    private String painLevel;
    @Column(name = "danger_level")
    private int dangerLevel;
    @ManyToMany(targetEntity = Disease.class, mappedBy = "negativeImpacts")
    private Set<Disease> diseases;

    @ManyToMany(targetEntity = Disease.class, mappedBy = "negativeImpacts")
    private Set<Visitation> visitations;


}
