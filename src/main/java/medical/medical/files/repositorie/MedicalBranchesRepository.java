package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.MedicalBranchEntity;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalBranchesRepository extends JpaRepository<MedicalBranchEntity, Long> {
    Optional<MedicalBranchEntity> findByName(MedicalBranchesEnum medicalBranchEnumExist);



}
