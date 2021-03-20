package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.ExaminationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ExaminationRepository extends JpaRepository<ExaminationEntity, Long> {
    Optional<ExaminationEntity> findById(long id);

    //To check if it work if not native query
    Set<ExaminationEntity> findAllByDoctorId(long doctorId);
}
