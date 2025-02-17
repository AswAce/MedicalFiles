package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.DoctorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

;import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

   ArrayList<Optional<DoctorEntity>>  findByFullName(String familyName);

    Optional<DoctorEntity> findTopByOrderByIdDesc();
}
