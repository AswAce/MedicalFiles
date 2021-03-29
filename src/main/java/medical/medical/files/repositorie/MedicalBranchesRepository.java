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


//    @Query(value =" DELETE medical_branches_doctors\n" +
//            "    FROM    medical_branches_doctors\n" +
//            "    INNER JOIN doctors\n" +
//            "    ON medical_branches_doctors.doctors_id = doctors.id\n" +
//            "    WHERE   id=1", nativeQuery = true)
//    void removeDoctor(long id);
}
