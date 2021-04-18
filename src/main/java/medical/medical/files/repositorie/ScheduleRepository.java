package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Long> {
}
