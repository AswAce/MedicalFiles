package medical.MedicalProject.entities;

import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "negative_impacts")
public class NegativeImpact extends IdBaseClass {
    @NonNull
    private String type;
    @Column(name = "pain_level")
    @NonNull
    private String painLevel;
    @Column(name = "danger_level")
    @NonNull
    private int dangerLevel;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Disease.class, mappedBy = "negativeImpacts")
    private Set<Disease> diseases;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Disease.class, mappedBy = "negativeImpacts")
    private Set<MedicalCheck> visitations;


}
