package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.PatientEntity;
;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    PatientEntity findById(long id);

//    @Query(value = "SELECT * FROM patients p\n" +
//            "join patients_examinations pe on pe.patients_id=p.id\n" +
//            "where pe.examinations_id=?1", nativeQuery = true)
//    PatientEntity findByExaminationId(long id);
}
