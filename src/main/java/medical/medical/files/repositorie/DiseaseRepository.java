package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.DiseaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

;import java.util.Optional;

@Repository
public interface DiseaseRepository extends JpaRepository<DiseaseEntity, Long> {

    Optional<DiseaseEntity> findByName(String name);
}
