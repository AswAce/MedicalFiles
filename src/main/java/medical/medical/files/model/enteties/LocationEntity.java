package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.PartOfTheBodyEnum;
import medical.medical.files.model.enums.SideOfTheBodyEnum;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class LocationEntity extends BaseEntity {


    @Enumerated(EnumType.STRING)
    @Column(name = "parts_of_the_body", nullable = false)
    private PartOfTheBodyEnum partOfTheBody;
    @Enumerated(EnumType.STRING)
    @Column(name = "sides_of_the_body")
    private SideOfTheBodyEnum sideOfTheBody;

    @Column(name = "exact_locations", columnDefinition = "TEXT")
    private String exactLocation;
}
