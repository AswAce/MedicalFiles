package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.TypeEnum;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "additional_data")
public class AdditionalDataEntity extends BaseEntity {

    private LocalDate date;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEnum type;
    @Column(columnDefinition = "text")
    private String file;
    @OneToOne(cascade = CascadeType.ALL)
    private LocationEntity locationEntity;
}
