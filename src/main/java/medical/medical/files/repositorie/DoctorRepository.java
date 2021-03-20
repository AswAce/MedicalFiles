package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.DoctorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    DoctorEntity findByFullName(String familyName);
}
