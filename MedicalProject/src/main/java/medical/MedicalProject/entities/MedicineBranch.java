package medical.MedicalProject.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "medicine_branch")
public class MedicineBranch extends IdBaseClass {

    @NonNull
    private String name;
    @NonNull
    private String description;

    @OneToMany(targetEntity = MedicalCheck.class, mappedBy = "medicineBranch")
    private Set<MedicalCheck> visitation;

    @OneToMany(targetEntity = Disease.class, mappedBy = "medicineBranch")
    private Set<Disease> diseases;
}
