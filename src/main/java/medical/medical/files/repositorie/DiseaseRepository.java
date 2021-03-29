package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.DiseaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

;import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DiseaseRepository extends JpaRepository<DiseaseEntity, Long> {

    Set<DiseaseEntity> findAllByName(String name);

    Optional<DiseaseEntity> findByName(String name);

   List< DiseaseEntity> findByNameAndType(String name, String type);
}
