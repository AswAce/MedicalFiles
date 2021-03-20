package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "schedules")

public class ScheduleEntity extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private Set<DaySchedule> days=new LinkedHashSet<>();
    //TODO implementation in doctors and into departments
}
