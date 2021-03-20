package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.PatientEntity;
;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    PatientEntity findById(long id);

}
