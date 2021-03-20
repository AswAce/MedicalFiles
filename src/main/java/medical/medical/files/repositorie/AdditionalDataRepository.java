package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.AdditionalDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalDataRepository extends JpaRepository<AdditionalDataEntity, Long> {
}
