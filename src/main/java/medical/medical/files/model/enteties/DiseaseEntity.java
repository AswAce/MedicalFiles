package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "diseases")
public class DiseaseEntity extends BaseEntity {

    @Column( nullable = false)
    private String name;

    private String type;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "is_curable")
    private boolean isCurable;
    @Column(name = "is_chronic")
    private boolean isChronic;

}
