package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.ReviewsEntity;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewsEntity, Long> {

    Set<ReviewsEntity> findAllByDepartmentOrderByLocalDateTimeAsc(MedicalBranchesEnum medicalBranchesEnum);

    Set<ReviewsEntity> findByDoctorId(long id);

    Set<ReviewsEntity> findByPatientId(long id);
}
